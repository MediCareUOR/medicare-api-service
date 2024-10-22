package com.rucreativedeveloper.medicare_api_service.dto.paginated;

import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDrugPaginatedDto {
    private long count;
    private List<ResponseDrugDto> dataList;
}
