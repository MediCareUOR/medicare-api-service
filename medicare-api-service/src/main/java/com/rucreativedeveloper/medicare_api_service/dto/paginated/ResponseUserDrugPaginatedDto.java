package com.rucreativedeveloper.medicare_api_service.dto.paginated;

import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseUserDrugDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDrugPaginatedDto {
    private long count;
    private List<ResponseUserDrugDto> dataList;
}
