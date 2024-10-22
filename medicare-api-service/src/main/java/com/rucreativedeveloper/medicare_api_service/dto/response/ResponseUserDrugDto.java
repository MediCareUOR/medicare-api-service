package com.rucreativedeveloper.medicare_api_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDrugDto {

    private String drugName;
    private String brandName;
    private String unitPrice;

    private ResponseUserPharmacyDto userPharmacyDto;

}
