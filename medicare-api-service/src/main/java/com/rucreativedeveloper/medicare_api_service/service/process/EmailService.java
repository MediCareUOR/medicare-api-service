package com.rucreativedeveloper.medicare_api_service.service.process;

import java.io.IOException;

public interface EmailService {
    public boolean sendUserSignupVerificationCode(String toEmail, String subject, String otp) throws IOException;
    public boolean sendPasswordResetVerificationCode(String toEmail, String subject, String otp) throws IOException;
    public boolean sendPharmacistWaiting(String toEmail, String subject) throws IOException;
    public boolean sendPharmacistVerified(String toEmail, String subject) throws IOException;

}
