package com.okta.auth.security.otp.service;

public interface OtpService {
    String generateAndSaveOtp(String identifier);

    boolean verifyOTP(String identifier, String otp,boolean remove);

    String getOTP(String identifier);

    boolean generateAndSendOtp(String identifier);

}
