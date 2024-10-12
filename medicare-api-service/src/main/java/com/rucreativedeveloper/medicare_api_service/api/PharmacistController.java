package com.rucreativedeveloper.medicare_api_service.api;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.service.PharmacistService;
import com.rucreativedeveloper.medicare_api_service.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pharmacist")
@RequiredArgsConstructor
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @PostMapping("/register-pharmacist")
    public ResponseEntity<StandardResponseDto> registerPharmacist(@RequestBody RequestPharmacistDto requestPharmacistDto) {

        pharmacistService.registerPharmacist(requestPharmacistDto);

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Pharmacist successfully registered",requestPharmacistDto), HttpStatus.CREATED
        );

    }


    @PutMapping("update-pharmacist/{pharmacistId}")
    public ResponseEntity<StandardResponseDto> updatePharmacist(@PathVariable(name = "pharmacistId") String pharmacistId, @RequestBody RequestPharmacistDto requestPharmacistDto) {

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Successfully updated",requestPharmacistDto), HttpStatus.OK
        );
    }

    @DeleteMapping("delete-pharmacist/{pharmacistId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> deletePharmacist(@PathVariable("pharmacistId") String pharmacistId) {


        return new ResponseEntity<>(
                new StandardResponseDto(201,"Successfully deleted",pharmacistId), HttpStatus.OK
        );
    }



}
