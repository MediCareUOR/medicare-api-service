package com.rucreativedeveloper.medicare_api_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePharmacyDto {

    private String pharmacyId;
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
