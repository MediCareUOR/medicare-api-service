package com.rucreativedeveloper.medicare_api_service.repository;

import com.rucreativedeveloper.medicare_api_service.entity.Drug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface DrugRepo extends JpaRepository<Drug,String> {

    @Query(value = "SELECT * FROM drug WHERE (drug_name LIKE %?1% OR drug_id LIKE %?1%) AND inventory_id = ?2 ORDER BY drug_name DESC",nativeQuery = true)
    public Page<Drug> findAllDrugsWithSearchText(String searchText,String inventoryId, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM drug WHERE (drug_name LIKE %?1% OR drug_id LIKE %?1%) AND inventory_id = ?2 ORDER BY drug_name DESC",nativeQuery = true)
    public long findAllDrugsCount(String searchText,String inventoryId);


}
