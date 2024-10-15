package com.rucreativedeveloper.medicare_api_service.api;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestDrugDto;
import com.rucreativedeveloper.medicare_api_service.service.DrugService;
import com.rucreativedeveloper.medicare_api_service.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;


    @PostMapping("/save-drug")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<StandardResponseDto> create(@RequestBody RequestDrugDto requestDrugDto,@RequestHeader("Authorization") String token){

        drugService.createDrug(requestDrugDto,token);

        return new ResponseEntity<>(
                new StandardResponseDto(
                        201,"You successfully saved your drug",requestDrugDto.getDrugName()
                ), HttpStatus.CREATED
        );



    }

    @PutMapping("/update-drug/{drugId}")
    @PreAuthorize("hasAnyRole('ROLE_PHARMACIST','ROLE_ADMIN')")

    public ResponseEntity<StandardResponseDto> update(@PathVariable(name="drugId") String drugId,@RequestBody RequestDrugDto requestDrugDto){

        drugService.updateDrug(drugId,requestDrugDto);

        return new ResponseEntity<>(
                new StandardResponseDto(
                        201,"You have successfully updated",drugId
                ), HttpStatus.OK
        );
    }


    @DeleteMapping("delete-drug/{drugId}")
    @PreAuthorize("hasAnyRole('ROLE_PHARMACIST','ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> delete(@PathVariable(name="drugId") String drugId){

        drugService.deleteDrug(drugId);

        return new ResponseEntity<>(
                new StandardResponseDto(
                        201,"You have successfully deleted",drugId
                ), HttpStatus.OK
        );

    }





}
