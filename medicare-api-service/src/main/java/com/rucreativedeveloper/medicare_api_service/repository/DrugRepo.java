package com.rucreativedeveloper.medicare_api_service.repository;

import com.rucreativedeveloper.medicare_api_service.entity.Drug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface DrugRepo extends JpaRepository<Drug,String> {

    @Query(value = "SELECT * FROM drug WHERE (drug_name LIKE %?1% OR drug_id LIKE %?1% OR brand_name LIKE %?1%) AND inventory_id = ?2 ORDER BY drug_name DESC",nativeQuery = true)
    public Page<Drug> findAllDrugsWithSearchText(String searchText,String inventoryId, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM drug WHERE (drug_name LIKE %?1% OR drug_id LIKE %?1% OR brand_name LIKE %?1%) AND inventory_id = ?2 ORDER BY drug_name DESC",nativeQuery = true)
    public long findAllDrugsCount(String searchText,String inventoryId);

    @Query(value = "SELECT DISTINCT d FROM drug d " +
            "JOIN d.inventory i " +
            "JOIN d.inventory.pharmacy p " +
            "WHERE (LOWER(p.district) LIKE LOWER(CONCAT('%', :district, '%'))) " +
            "AND (LOWER(d.drugName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(d.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
            countQuery = "SELECT COUNT(DISTINCT d) FROM drug d " +
                    "JOIN d.inventory i " +
                    "JOIN d.inventory.pharmacy p " +
                    "WHERE LOWER(p.district) LIKE LOWER(CONCAT('%', :district, '%')) " +
                    "AND (LOWER(d.drugName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
                    "OR LOWER(d.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Drug> findDrugsWithPharmacyDetails(
            @Param("district") String district,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query(value = "SELECT DISTINCT d FROM drug d " +
            "JOIN d.inventory i " +
            "JOIN d.inventory.pharmacy p " +
            "WHERE LOWER(d.drugName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(d.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))",
            countQuery = "SELECT COUNT(DISTINCT d) FROM drug d " +
                    "JOIN d.inventory i " +
                    "JOIN d.inventory.pharmacy p " +
                    "WHERE LOWER(d.drugName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
                    "OR LOWER(d.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Drug> findDrugsWithPharmacyDetailsByNameOrBrand(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("SELECT COUNT(DISTINCT d) FROM drug d " +
            "JOIN d.inventory i " +
            "JOIN d.inventory.pharmacy p " +
            "WHERE LOWER(p.district) LIKE LOWER(CONCAT('%', :district, '%')) " +
            "AND (LOWER(d.drugName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(d.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    long findDrugsCountWithPharmacyDetails(
            @Param("searchTerm") String searchTerm,
            @Param("district") String district
    );

    @Query("SELECT COUNT(DISTINCT d) FROM drug d " +
            "JOIN d.inventory i " +
            "JOIN d.inventory.pharmacy p " +
            "WHERE LOWER(d.drugName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(d.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long findDrugsCountWithPharmacyDetailsByNameOrBrand(
            @Param("searchTerm") String searchTerm
    );


}
