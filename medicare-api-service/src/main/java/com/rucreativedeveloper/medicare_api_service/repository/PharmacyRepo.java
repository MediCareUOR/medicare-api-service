package com.rucreativedeveloper.medicare_api_service.repository;

import com.rucreativedeveloper.medicare_api_service.entity.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, String> {

    @Query(value = "SELECT * FROM pharmacy WHERE district LIKE %?1% ORDER BY pharmacy_name DESC",nativeQuery = true)
    public Page<Pharmacy> findPharmacyByDistrict(String district, Pageable pageable);



}
