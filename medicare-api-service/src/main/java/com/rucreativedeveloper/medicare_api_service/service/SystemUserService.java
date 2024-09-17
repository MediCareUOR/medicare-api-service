package com.rucreativedeveloper.medicare_api_service.service;

import com.rucreativedeveloper.medicare_api_service.dto.request.RequestSystemUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SystemUserService extends UserDetailsService {
    public void signup(RequestSystemUserDto dto);
    public void initializeSystemAdmin();
}
