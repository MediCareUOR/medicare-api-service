package com.rucreativedeveloper.medicare_api_service;

import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import com.rucreativedeveloper.medicare_api_service.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MedicareApiServiceApplication implements CommandLineRunner {

	private final UserRoleService userRoleService;
	private final SystemUserService systemUserService;
	public static void main(String[] args) {
		SpringApplication.run(MedicareApiServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRoleService.initializeUserRoles();
		systemUserService.initializeSystemAdmin();
	}
}
