package com.rucreativedeveloper.medicare_api_service.service.Impl;


import com.rucreativedeveloper.medicare_api_service.entity.UserRole;
import com.rucreativedeveloper.medicare_api_service.repository.UserRoleRepo;
import com.rucreativedeveloper.medicare_api_service.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepo roleRepo;

    @Override
    public void initializeUserRoles() {
        List<UserRole> allSelectedData = roleRepo.findAll();
        if (allSelectedData.isEmpty()) {
            UserRole user = UserRole.builder().roleId(UUID.randomUUID().toString()).roleName("USER").build();
            UserRole admin = UserRole.builder().roleId(UUID.randomUUID().toString()).roleName("ADMIN").build();
            UserRole pharmacist = UserRole.builder().roleId(UUID.randomUUID().toString()).roleName("PHARMACIST").build();


            roleRepo.saveAll(List.of(user, admin, pharmacist));
        }
    }
}
