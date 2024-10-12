package com.rucreativedeveloper.medicare_api_service.service;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestSystemUserDto;

import org.springframework.security.core.userdetails.UserDetailsService;



import java.io.IOException;


public interface SystemUserService extends UserDetailsService {
    public void signupUser(RequestSystemUserDto dto) throws IOException;

    public boolean verifyEmail(String email, String otp);

    public void initializeSystemAdmin();


}
