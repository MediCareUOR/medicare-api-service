package com.rucreativedeveloper.medicare_api_service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MedicareApiServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MedicareApiServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
