package com.rucreativedeveloper.medicare_api_service.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDrugInventoryDto {
    private String inventoryId;
    private Date lastUpdateDate;

    private ResponsePharmacyDto responsePharmacy;

}
