package com.rucreativedeveloper.medicare_api_service.repository;

import com.rucreativedeveloper.medicare_api_service.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, String> {


}
