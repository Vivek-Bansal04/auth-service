package com.okta.auth.security.otp.service;

import com.okta.auth.security.communication.service.SmsService;
import com.okta.auth.security.otp.OtpRepository;
import com.okta.auth.security.otp.entity.Otp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final SmsService smsService;

    private final OtpRepository otpRepository;

    @Autowired
    public OtpServiceImpl(
            SmsService smsService,
            OtpRepository otpRepository
    ){
        this.smsService = smsService;
        this.otpRepository = otpRepository;
    }

    public static List<String> demoMobileNos = new ArrayList<String>() {
        {
            add("9999999999");
            add("8888888888");
            add("77777777777");
        }
    };
    @Override
    @Transactional
    public String generateOtp(String identifier) {
        Otp otp = otpRepository.findOtpByIdentifier(identifier);

        String randomOtp = generateOTP(identifier, 60 * 1000); // 1000 min expiry

        if(Objects.isNull(otp)){
            Otp otp1 = new Otp();
            otp1.setIdentifier(identifier);
            otp1.setCreatedAt(LocalDateTime.now());
            otp1.setValue(randomOtp);
            otpRepository.save(otp1);
        }else{
            otp.setCreatedAt(LocalDateTime.now());
            otp.setValue(randomOtp);
            otpRepository.save(otp);
        }

        return randomOtp;
    }


    private String generateOTP(String identifier, int expiry) {
        try {
            String otp;
            if (demoMobileNos.contains(identifier) || identifier.startsWith("2222")) {
                otp = "123456";
            }  else {
                otp = OTPGenerator.generateOTP(6);
            }
            //TODO save in db
            return otp;
        } catch (Exception e) {
            log.error("Cannot generate OTP for msisdn [{}]", identifier, e);
        }
        return null;
    }



    @Override
    public boolean verifyOTP(String identifier, String randomOtp,boolean removeFromCache) {
        Otp otp = otpRepository.findOtpByIdentifier(identifier);
        boolean isVerified = otp.getValue().equals(randomOtp);
        if(isVerified && removeFromCache){
            otpRepository.delete(otp);
        }
        return isVerified;
    }

    @Override
    public String getOTP(String identifier) {
        return null;
    }

    @Override
    public boolean generateAndSendOtp(String identifier) {
        String otp = generateOtp(identifier);
        if(!demoMobileNos.contains(identifier) && !identifier.startsWith("2222")){
            //smsService.send(identifier, otp);
        }

        return true;
    }
}
