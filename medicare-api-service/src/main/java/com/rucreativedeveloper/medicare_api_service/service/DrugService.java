package com.rucreativedeveloper.medicare_api_service.service;

import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponsePharmacistPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;


public interface DrugService {
    public void createDrug(RequestDrugDto requestDrugDto,String token);

    void updateDrug(String drugId, RequestDrugDto requestDrugDto);

    void deleteDrug(String drugId);

    public ResponseDrugDto findPharmacist(String drugId);

    public ResponsePharmacistPaginatedDto findAllPharmacist(String searchText, int page, int size);


}
