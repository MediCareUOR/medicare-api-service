package com.rucreativedeveloper.medicare_api_service.service.Impl;


import com.rucreativedeveloper.medicare_api_service.dto.request.RequestSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.entity.SystemUser;
import com.rucreativedeveloper.medicare_api_service.entity.UserRole;
import com.rucreativedeveloper.medicare_api_service.exception.DuplicateEntryException;
import com.rucreativedeveloper.medicare_api_service.exception.EntryNotFoundException;
import com.rucreativedeveloper.medicare_api_service.repository.SystemUserRepo;
import com.rucreativedeveloper.medicare_api_service.repository.UserRoleRepo;
import com.rucreativedeveloper.medicare_api_service.security.ApplicationSecurityUser;
import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import com.rucreativedeveloper.medicare_api_service.util.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.rucreativedeveloper.medicare_api_service.security.ApplicationUserRole.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepo systemUserRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final Generator generator;

    @Override
    public void signup(RequestSystemUserDto dto) {
        Optional<SystemUser> selectedUserData = systemUserRepo.findByUsername(dto.getUsername());
        if (selectedUserData.isPresent()) {
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
                .isAccountNonExpired(true)
                .roles(roles)// USER,ADMIN,PHARMACIST
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(false)
                .password(passwordEncoder.encode(dto.getPassword()))// encrypt
                .username(dto.getUsername())
                .otp(generator.generateOtp(4))
                .build();

        systemUserRepo.save(systemUser);
    }

    @Override
    public void verifyEmail(String email, String otp) {



    }


    @Override
    public void initializeSystemAdmin() {
        Optional<SystemUser> selectedUserData = systemUserRepo.findByUsername("medicare.uor@gmail.com");
        if (selectedUserData.isPresent()) {
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
                .password(passwordEncoder.encode("1234"))// encrypt
                .username("medicare.uor@gmail.com")
                .build();

        systemUserRepo.save(systemUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SystemUser> selectedUserData = systemUserRepo.findByUsername(username);
        if(selectedUserData.isEmpty()){
            throw new EntryNotFoundException(String.format("username %s not found",username));
        }

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for(UserRole r : selectedUserData.get().getRoles()){
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
                selectedUserData.get().getUsername(),
                selectedUserData.get().getPassword(),
                selectedUserData.get().isAccountNonExpired(),
                selectedUserData.get().isAccountNonLocked(),
                selectedUserData.get().isAccountNonExpired(),
                selectedUserData.get().isEnabled(),
                grantedAuthorities
        );

    }
}
