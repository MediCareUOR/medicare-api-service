package com.rucreativedeveloper.medicare_api_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseSystemUserDto {

    private String userId;
    private String userName;
    private String password;


}
