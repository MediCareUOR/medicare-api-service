package com.rucreativedeveloper.medicare_api_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserPharmacyDto {

    private String pharmacyName;
    private String address;
    private String contactNumber;
    private String district;
    private String city;
    private String longitude;
    private String latitude;

}
