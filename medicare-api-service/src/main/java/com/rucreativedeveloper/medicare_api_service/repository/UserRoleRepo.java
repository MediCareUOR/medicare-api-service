package com.rucreativedeveloper.medicare_api_service.repository;


import com.rucreativedeveloper.medicare_api_service.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<UserRole,String> {

    public Optional<UserRole> findByRoleName(String role);





}
