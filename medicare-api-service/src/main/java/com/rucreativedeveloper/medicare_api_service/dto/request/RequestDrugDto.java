package com.rucreativedeveloper.medicare_api_service.dto.request;

import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugInventoryDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDrugDto {

    private String drugName;
    private String brandName;
    private String unitPrice;
    private String drugDescription;
    private String stockQty;



}
