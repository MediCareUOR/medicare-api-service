package com.rucreativedeveloper.medicare_api_service.service.Impl;


import com.rucreativedeveloper.medicare_api_service.config.JwtConfig;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.entity.SystemUser;
import com.rucreativedeveloper.medicare_api_service.entity.UserRole;
import com.rucreativedeveloper.medicare_api_service.exception.DuplicateEntryException;
import com.rucreativedeveloper.medicare_api_service.exception.EntryNotFoundException;
import com.rucreativedeveloper.medicare_api_service.repository.SystemUserRepo;
import com.rucreativedeveloper.medicare_api_service.repository.UserRoleRepo;
import com.rucreativedeveloper.medicare_api_service.security.ApplicationSecurityUser;
import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import com.rucreativedeveloper.medicare_api_service.service.process.EmailService;
import com.rucreativedeveloper.medicare_api_service.util.Generator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.*;

import static com.rucreativedeveloper.medicare_api_service.security.ApplicationUserRole.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepo systemUserRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final Generator generator;
    private final EmailService emailService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Override
    public void signupUser(RequestSystemUserDto dto) throws IOException {

        SystemUser selectedUserData = systemUserRepo.findByUsername(dto.getUsername());

        String otp= generator.generateOtp(4);

        if (selectedUserData!=null) {
            throw new DuplicateEntryException("User email already exists");
        }

        Optional<UserRole> selectedUserRoleData = userRoleRepo.findByRoleName("USER");

        if(selectedUserRoleData.isEmpty()){
            throw new EntryNotFoundException("Role was not found");
        }

        Set<UserRole> roles = new HashSet<>();
        roles.add(selectedUserRoleData.get());

        SystemUser systemUser = SystemUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(dto.getUsername())
                .isAccountNonExpired(true)
                .roles(roles)// USER,ADMIN,PHARMACIST
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(false)
                .password(passwordEncoder.encode(dto.getPassword()))// encrypt
                .createdDate(new Date())
                .otp(otp)
                .build();

        systemUserRepo.save(systemUser);

        emailService.sendUserSignupVerificationCode(systemUser.getUsername(), "Verify Your Email Address for MediCare Access",otp);


    }

    @Override
    public boolean verifyEmail(String email, String otp) {

        SystemUser user=systemUserRepo.findByUsername(email);

        if(user.isEnabled()){
            throw new DuplicateEntryException("User Already Verified");
        }

        if(user.getOtp()==otp){
            user.setEnabled(true);
            systemUserRepo.save(user);
            return true;
        }else{
            throw new EntryNotFoundException("You Entered A Wrong OTP");
        }

    }


    @Override
    public void initializeSystemAdmin() {
       SystemUser selectedUserData = systemUserRepo.findByUsername("medicare.uor@gmail.com");
        if (selectedUserData!=null) {
            return;
        }

        Optional<UserRole> selectedUserRoleData = userRoleRepo.findByRoleName("ADMIN");

        if(selectedUserRoleData.isEmpty()){
            throw new EntryNotFoundException("Role was not found");
        }

        Set<UserRole> roles = new HashSet<>();
        roles.add(selectedUserRoleData.get());

        SystemUser systemUser = SystemUser.builder()
                .userId(UUID.randomUUID().toString())
                .isAccountNonExpired(true)
                .roles(roles)// ADMIN
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .createdDate(new Date())
                .password(passwordEncoder.encode("1234"))// encrypt
                .username("medicare.uor@gmail.com")
                .build();

        systemUserRepo.save(systemUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser selectedUserData = systemUserRepo.findByUsername(username);
        if(selectedUserData==null){
            throw new EntryNotFoundException(String.format("username %s not found",username));
        }

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for(UserRole r : selectedUserData.getRoles()){
            if(r.getRoleName().equals("ADMIN")){
                grantedAuthorities.addAll(ADMIN.grantedAuthorities());
            }
            if(r.getRoleName().equals("PHARMACIST")){
                grantedAuthorities.addAll(PHARMACIST.grantedAuthorities());
            }
            if(r.getRoleName().equals("USER")){
                grantedAuthorities.addAll(USER.grantedAuthorities());
            }
        }

        return new ApplicationSecurityUser(
                selectedUserData.getUsername(),
                selectedUserData.getPassword(),
                selectedUserData.isAccountNonExpired(),
                selectedUserData.isAccountNonLocked(),
                selectedUserData.isAccountNonExpired(),
                selectedUserData.isEnabled(),
                grantedAuthorities
        );

    }

    public SystemUser getUserByToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            return null;
        }
        assert token != null;
        ResponseSystemUserDto userData = getAllSystemUserData(token);
        Optional<SystemUser> user = systemUserRepo.findById(userData.getUserId());
        if (user.isEmpty()) {
            throw new EntryNotFoundException("User Not Found");
        }
        return user.get();
    }


    @Override
    public ResponseSystemUserDto getAllSystemUserData(String token) {
        String realToken = token.replace(jwtConfig.getTokenPrefix(), "");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(realToken);
        String username = claimsJws.getBody().getSubject();
        SystemUser selectedUser = systemUserRepo.findByUsername(username);
        if (selectedUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new ResponseSystemUserDto(
                selectedUser.getUserId(),
                selectedUser.getUsername(),
                selectedUser.getRoles().toString()
        );
    }
}
