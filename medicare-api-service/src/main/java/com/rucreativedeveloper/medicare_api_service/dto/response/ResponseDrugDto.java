package com.rucreativedeveloper.medicare_api_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDrugDto {

    private String drugId;
    private String drugName;
    private String drugDescription;
    private String stockQty;

    private ResponseDrugInventoryDto responseDrugInventoryDto;

}
