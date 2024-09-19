package com.rucreativedeveloper.medicare_api_service.service.process.Impl;

import com.rucreativedeveloper.medicare_api_service.service.process.EmailService;
import com.rucreativedeveloper.medicare_api_service.util.EmailTemplateHelper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailTemplateHelper emailTemplateHelper;



    @Value("${from.email}")
    private String senderEmail;

    @Override
    public boolean sendUserSignupVerificationCode(String toEmail, String subject, String otp) throws IOException {

        try {
            String htmlBody = emailTemplateHelper.loadHtmlTemplate("templates/medi-care-send-login-verification-email-template.html");
            htmlBody = htmlBody.replace("${otp}", otp);
            htmlBody = htmlBody.replace("${year}", String.valueOf(Year.now().getValue()));

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendPasswordResetVerificationCode(String toEmail, String subject, String otp) throws IOException {

        try {
            String htmlBody = emailTemplateHelper.loadHtmlTemplate("templates/medi-care-send-reset-password-verification-email-template.html");
            htmlBody = htmlBody.replace("${otp}", otp);
            htmlBody = htmlBody.replace("${year}", String.valueOf(Year.now().getValue()));

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
