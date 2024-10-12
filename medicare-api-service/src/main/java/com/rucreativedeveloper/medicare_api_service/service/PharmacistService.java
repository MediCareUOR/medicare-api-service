package com.rucreativedeveloper.medicare_api_service.service;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.entity.Pharmacist;

public interface PharmacistService {

    void registerPharmacist(RequestPharmacistDto requestPharmacistDto);

    void updatePharmacist(String token, RequestPharmacistDto requestPharmacistDto);

    void deletePharmacist(String pharmacistId);


}
