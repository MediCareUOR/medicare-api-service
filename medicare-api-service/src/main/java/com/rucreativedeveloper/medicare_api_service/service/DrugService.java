package com.rucreativedeveloper.medicare_api_service.service;

import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponseDrugPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponsePharmacistPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponseUserDrugPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;


public interface DrugService {
    public void createDrug(RequestDrugDto requestDrugDto,String token);

    void updateDrug(String drugId, RequestDrugDto requestDrugDto);

    void deleteDrug(String drugId);

    public ResponseDrugDto findDrug(String drugId,String token);

    public ResponseDrugPaginatedDto findAllDrugs(String token, String searchText, int page, int size);

    public ResponseUserDrugPaginatedDto userFindAllDrugs(String searchText, String district, int page, int size);


}
