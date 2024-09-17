package com.rucreativedeveloper.medicare_api_service.api;


import com.rucreativedeveloper.medicare_api_service.dto.request.RequestSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import com.rucreativedeveloper.medicare_api_service.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system-users")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService userService;

    @PostMapping("/visitor/signup") // http://localhost:8081/api/v1/system-users/visitor/signup
    public ResponseEntity<StandardResponseDto> signup(@RequestBody RequestSystemUserDto dto) {
        userService.signup(dto);
        return new ResponseEntity<>(
                new StandardResponseDto(201, "user was registered", null),
                HttpStatus.CREATED
        );
    }
}
