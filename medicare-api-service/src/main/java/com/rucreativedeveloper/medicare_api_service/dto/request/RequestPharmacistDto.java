package com.rucreativedeveloper.medicare_api_service.dto.request;

import com.rucreativedeveloper.medicare_api_service.entity.Pharmacy;
import com.rucreativedeveloper.medicare_api_service.entity.SystemUser;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPharmacistDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private RequestPharmacyDto requestPharmacy;

    private RequestSystemUserDto requestSystemUser;


}
