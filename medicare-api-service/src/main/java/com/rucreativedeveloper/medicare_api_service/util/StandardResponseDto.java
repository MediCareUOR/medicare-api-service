package com.rucreativedeveloper.medicare_api_service.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponseDto {
    private int code;
    private String message;
    private Object data;
}
