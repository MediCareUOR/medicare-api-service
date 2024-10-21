package com.rucreativedeveloper.medicare_api_service.api;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugDto;
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
                        201,"You have successfully updated",requestDrugDto
                ), HttpStatus.OK
        );
    }


    @DeleteMapping("/delete-drug/{drugId}")
    @PreAuthorize("hasAnyRole('ROLE_PHARMACIST','ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> delete(@PathVariable(name="drugId") String drugId){

        drugService.deleteDrug(drugId);

        return new ResponseEntity<>(
                new StandardResponseDto(
                        201,"You have successfully deleted",drugId
                ), HttpStatus.OK
        );

    }


    @GetMapping("/get-by-id/{drugId}")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")

    public ResponseEntity<StandardResponseDto> getById(@PathVariable(name="drugId") String drugId,@RequestHeader("Authorization") String token){

        ResponseDrugDto dto=drugService.findDrug(drugId,token);

        if(dto==null){
            return new ResponseEntity<>(
                    new StandardResponseDto(
                            404,"Drug Not found",drugId
                    ), HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new StandardResponseDto(
                        201,"Your Drug is:",dto
                ), HttpStatus.OK
        );


    }

    @GetMapping("/get-all-drugs")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")

    public ResponseEntity<StandardResponseDto> getAllDrugs(@RequestHeader("Authorization") String token,
                                                           @RequestParam String searchText,
                                                           @RequestParam int page,
                                                           @RequestParam int size){

        return new ResponseEntity<>(
                new StandardResponseDto(200, "drug data",
                        drugService.findAllDrugs(token , searchText, page, size)),
                HttpStatus.OK
        );
    }


}
