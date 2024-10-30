package com.rucreativedeveloper.medicare_api_service.dto.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePharmacistUserDto {

    private String userName;

    private boolean isEnable;
}
