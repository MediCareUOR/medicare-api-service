package com.rucreativedeveloper.medicare_api_service.api;


import com.rucreativedeveloper.medicare_api_service.dto.request.RequestSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import com.rucreativedeveloper.medicare_api_service.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/system-users")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService userService;

    @PostMapping("/visitor/signup") // http://localhost:8081/api/v1/system-users/visitor/signup
    public ResponseEntity<StandardResponseDto> signup(@RequestBody RequestSystemUserDto dto) throws IOException {
        userService.signupUser(dto);
        return new ResponseEntity<>(
                new StandardResponseDto(201, "Verify Your Account with OTP", null),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path={"/verify-email"},params = {"otp", "email"})
    public ResponseEntity<StandardResponseDto> verifyEmail(@RequestParam String otp, @RequestParam String email) {

        boolean isVerified = userService.verifyEmail(otp, email);
       if(isVerified) {
           return new ResponseEntity<>(
                   new StandardResponseDto(201, "Email successfully verified", null), HttpStatus.OK
           );
       }else{
           return new ResponseEntity<>(
                   new StandardResponseDto(400,
                           "Invalid OTP. Please insert the correct code to verify your email.", null),
                   HttpStatus.BAD_REQUEST
           );
       }

    }




}
