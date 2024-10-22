package com.rucreativedeveloper.medicare_api_service.repository;

import com.rucreativedeveloper.medicare_api_service.entity.Pharmacist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface PharmacistRepo extends JpaRepository<Pharmacist, String> {


    @Query(value = "SELECT * FROM pharmacist WHERE pharmacist_Id LIKE %?1%",nativeQuery = true)
    public List<Pharmacist> getAllPharmacists(String searchText, Pageable pageable);

    @Query(value="SELECT COUNT(*) pharmacist WHERE pharmacist_Id LIKE %?1%",nativeQuery = true)
    public Long getPharmacistCount(String searchText);



}