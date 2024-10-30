package com.rucreativedeveloper.medicare_api_service.service;

import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponsePharmacistPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;
import com.rucreativedeveloper.medicare_api_service.entity.Pharmacist;

import java.io.IOException;
import java.util.List;

public interface PharmacistService {

    void registerPharmacist(RequestPharmacistDto requestPharmacistDto) throws IOException;

    void updatePharmacist(String pharmacistId, RequestPharmacistDto requestPharmacistDto);

    void deletePharmacist(String pharmacistId);

    public ResponsePharmacistDto findPharmacist(String pharmacistId);

    public ResponsePharmacistPaginatedDto findAllPharmacist(String searchText, int page, int size);

    void verifyPharmacist(String pharmacistId) throws IOException;

    public long pharmacistCount();
}
