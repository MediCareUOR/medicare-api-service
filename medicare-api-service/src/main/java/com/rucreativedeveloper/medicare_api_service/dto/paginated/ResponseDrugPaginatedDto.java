package com.rucreativedeveloper.medicare_api_service.dto.paginated;

import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDrugPaginatedDto {
    private long count;
    private List<ResponsePharmacistDto> dataList;
}
