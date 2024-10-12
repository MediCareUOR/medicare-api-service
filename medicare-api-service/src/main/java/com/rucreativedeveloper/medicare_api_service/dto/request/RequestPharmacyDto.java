package com.rucreativedeveloper.medicare_api_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPharmacyDto {

    private String pharmacyName;
    private String registerNumber;
    private String contactNumber;
    private String postal;
    private String district;
    private String address;
    private String city;
    private String longitude;
    private String latitude;
}
