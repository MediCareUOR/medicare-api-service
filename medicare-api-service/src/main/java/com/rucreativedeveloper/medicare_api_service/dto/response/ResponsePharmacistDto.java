package com.rucreativedeveloper.medicare_api_service.dto.response;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacyDto;
import com.rucreativedeveloper.medicare_api_service.entity.SystemUser;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePharmacistDto {

    private String pharmacistId;
    private String pharmacistName;
    private String phoneNumber;



}
