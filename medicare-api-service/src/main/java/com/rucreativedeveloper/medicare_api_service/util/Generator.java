package com.rucreativedeveloper.medicare_api_service.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Component
public class Generator {

    public String generateOtp(int length) {

        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {

            char randomUpperCaseLetter = (char) ('A' + random.nextInt(26));

            sb.append(randomUpperCaseLetter);
        }

        return sb.toString();
    }
}
