package com.rucreativedeveloper.medicare_api_service.dto.paginated;

import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePharmacistPaginatedDto {

    private List<ResponsePharmacistDto> dataList;
    private long count;

}
