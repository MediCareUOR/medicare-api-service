package com.rucreativedeveloper.medicare_api_service.api;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;
import com.rucreativedeveloper.medicare_api_service.service.PharmacistService;
import com.rucreativedeveloper.medicare_api_service.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/pharmacist")
@RequiredArgsConstructor
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @PostMapping("/register-pharmacist")
    public ResponseEntity<StandardResponseDto> registerPharmacist(@RequestBody RequestPharmacistDto requestPharmacistDto) throws IOException {

        pharmacistService.registerPharmacist(requestPharmacistDto);

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Pharmacist successfully registered",requestPharmacistDto), HttpStatus.CREATED
        );


    }


    @PutMapping("update-pharmacist/{pharmacistId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PHARMACIST')")
    public ResponseEntity<StandardResponseDto> updatePharmacist(@PathVariable(name = "pharmacistId") String pharmacistId, @RequestBody RequestPharmacistDto requestPharmacistDto) {

        pharmacistService.updatePharmacist(pharmacistId, requestPharmacistDto);

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Successfully updated",requestPharmacistDto), HttpStatus.OK
        );
    }

    @DeleteMapping("delete-pharmacist/{pharmacistId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> deletePharmacist(@PathVariable("pharmacistId") String pharmacistId) {

        pharmacistService.deletePharmacist(pharmacistId);

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Successfully deleted",pharmacistId), HttpStatus.OK
        );



    }



    @GetMapping("get-by-id/{pharmacistId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> getPharmacistById(@PathVariable("pharmacistId") String pharmacistId) {

        ResponsePharmacistDto responsePharmacistDto=pharmacistService.findPharmacist(pharmacistId);

        if(responsePharmacistDto==null) {
            return new ResponseEntity<>(
                    new StandardResponseDto(201,"Something went wrong",null), HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Successfully retrieved",responsePharmacistDto), HttpStatus.OK
        );
    }

    @GetMapping("/admin/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> getAllPharmacist(
            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size
    ){

        return new ResponseEntity<>(
                new StandardResponseDto(201,"Your Pharmacists are",pharmacistService.findAllPharmacist(searchText, page, size)), HttpStatus.OK
        );

    }


    @PutMapping("/admin/verify-pharmacist/{pharmacistId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponseDto> verifyPharmacist(@PathVariable(name="pharmacistId") String pharmacistId) throws IOException {

        pharmacistService.verifyPharmacist(pharmacistId);
        return new ResponseEntity<>(
                new StandardResponseDto(201,"You have successfully activated pharmacist:",pharmacistId), HttpStatus.OK
        );

    }


}
