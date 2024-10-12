package com.rucreativedeveloper.medicare_api_service.service.Impl;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.entity.Pharmacist;
import com.rucreativedeveloper.medicare_api_service.entity.Pharmacy;
import com.rucreativedeveloper.medicare_api_service.entity.SystemUser;
import com.rucreativedeveloper.medicare_api_service.entity.UserRole;
import com.rucreativedeveloper.medicare_api_service.repository.PharmacistRepo;
import com.rucreativedeveloper.medicare_api_service.repository.PharmacyRepo;
import com.rucreativedeveloper.medicare_api_service.repository.SystemUserRepo;
import com.rucreativedeveloper.medicare_api_service.repository.UserRoleRepo;
import com.rucreativedeveloper.medicare_api_service.service.PharmacistService;
import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PharmacistServiceImpl implements PharmacistService {

    private final UserRoleRepo userRoleRepo;
    private final PharmacistRepo pharmacistRepo;
    private final SystemUserRepo systemUserRepo;
    private final SystemUserService systemUserService;
    private final PharmacyRepo pharmacyRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerPharmacist(RequestPharmacistDto requestPharmacistDto) {

        SystemUser savedSystemUser=systemUserRepo.findByUsername(requestPharmacistDto.getRequestSystemUser().getUsername());

        if(savedSystemUser!=null){
         return;
        }

        Optional<UserRole> selectedUserRoleData = userRoleRepo.findByRoleName("PHARMACIST");

        if(selectedUserRoleData.isPresent()){


            Set<UserRole> roles = new HashSet<>();
            roles.add(selectedUserRoleData.get());

            SystemUser newSystemUser=SystemUser.builder()
                    .userId(UUID.randomUUID().toString())
                    .username(requestPharmacistDto.getRequestSystemUser().getUsername())
                    .password(passwordEncoder.encode(requestPharmacistDto.getRequestSystemUser().getPassword()))
                    .createdDate(new Date())
                    .roles(roles)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(false)
                    .build();

            systemUserRepo.save(newSystemUser);

            Pharmacy pharmacy=Pharmacy.builder()
                    .pharmacyId(UUID.randomUUID().toString())
                    .pharmacyName(requestPharmacistDto.getRequestPharmacy().getPharmacyName())
                    .registerNumber(requestPharmacistDto.getRequestPharmacy().getRegisterNumber())
                    .contactNumber(requestPharmacistDto.getRequestPharmacy().getContactNumber())
                    .postal(requestPharmacistDto.getRequestPharmacy().getPostal())
                    .district(requestPharmacistDto.getRequestPharmacy().getDistrict())
                    .address(requestPharmacistDto.getRequestPharmacy().getAddress())
                    .city(requestPharmacistDto.getRequestPharmacy().getCity())
                    .longitude(requestPharmacistDto.getRequestPharmacy().getLongitude())
                    .latitude(requestPharmacistDto.getRequestPharmacy().getLatitude())
                    .build();

            pharmacyRepo.save(pharmacy);

            Pharmacist pharmacist=Pharmacist.builder()
                    .pharmacistId(UUID.randomUUID().toString())
                    .firstName(requestPharmacistDto.getFirstName())
                    .lastName(requestPharmacistDto.getLastName())
                    .phoneNumber(requestPharmacistDto.getPhoneNumber())
                    .systemUser(newSystemUser)
                    .pharmacy(pharmacy)
                    .build();

            pharmacistRepo.save(pharmacist);


        }


    }

    @Override
    public void updatePharmacist(String token, RequestPharmacistDto requestPharmacistDto) {

    }

    @Override
    public void deletePharmacist(String pharmacistId) {

    }


}
