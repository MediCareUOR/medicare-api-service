package com.rucreativedeveloper.medicare_api_service.repository;

import com.rucreativedeveloper.medicare_api_service.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepo extends JpaRepository<SystemUser,String> {
    public Optional<SystemUser> findByUsername(String username);
}
