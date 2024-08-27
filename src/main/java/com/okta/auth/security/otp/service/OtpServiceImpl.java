package com.okta.auth.security.otp.service;

import com.okta.auth.security.cache.service.ICacheService;
import com.okta.auth.security.communication.service.SmsService;
import com.okta.auth.security.otp.OtpRepository;
import com.okta.auth.security.utils.exceptions.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final SmsService smsService;

    private final OtpRepository otpRepository;

    @Qualifier("redisCacheService")
    private final ICacheService cacheService;

    public static List<String> demoMobileNos = new ArrayList<String>() {
        {
            add("9999999999");
            add("8888888888");
            add("77777777777");
        }
    };

    private static final long OTP_EXPIRY_TIME = 60*100;

    private static final String REDIS_OTP_HASH_KEY_PREFIX = "otp";

    @Autowired
    public OtpServiceImpl(
            SmsService smsService,
            OtpRepository otpRepository,
            ICacheService cacheService
    ){
        this.smsService = smsService;
        this.otpRepository = otpRepository;
        this.cacheService = cacheService;
    }


    @Override
    @Transactional
    public String generateAndSaveOtp(String identifier) {
        validateIdentifier(identifier);
        String key = String.format("%s-%s", REDIS_OTP_HASH_KEY_PREFIX, identifier);

        Object otp = cacheService.getFromCache(key);
        if(otp!=null){
            //as user wanted to regenerate otp so returned same otp with updated expiration time
            cacheService.updateKeyExpiration(key,OTP_EXPIRY_TIME);
            return otp.toString();
        }
        String randomOtp = generateOTP(identifier);
        cacheService.putInCache(key,randomOtp,OTP_EXPIRY_TIME);
        return randomOtp;
    }

    @Override
    public boolean verifyOTP(String identifier, String randomOtp,boolean removeFromCache) {
        String key = String.format("%s-%s", REDIS_OTP_HASH_KEY_PREFIX, identifier);
        String otp = getOTP(identifier);
        boolean isVerified = otp != null && otp.equals(randomOtp);
        if(removeFromCache){
            cacheService.delKeyFromCache(key);
        }
        return isVerified;
    }

    @Override
    public String getOTP(String identifier) {
        String key = String.format("%s-%s", REDIS_OTP_HASH_KEY_PREFIX, identifier);
        Object otp = cacheService.getFromCache(key);
        return otp!=null?otp.toString():null;
    }

    @Override
    public boolean generateAndSendOtp(String identifier) {
        //TODO integrate sms service
        String otp = generateAndSaveOtp(identifier);
        if(!demoMobileNos.contains(identifier) && !identifier.startsWith("2222")){
            //smsService.send(identifier, otp);
        }

        return true;
    }


    private String generateOTP(String identifier) {
        try {
            String otp;
            if (demoMobileNos.contains(identifier) || identifier.startsWith("2222")) {
                otp = "123456";
            }  else {
                otp = OTPGenerator.generateOTP(6);
            }
            return otp;
        } catch (Exception e) {
            log.error("Cannot generate OTP for identifier [{}]", identifier, e);
        }
        return null;
    }

    public boolean validateIdentifier(String mobileNumber) {
        // ADD A LOG FOR IP AND IDENTIFIER
        if (StringUtils.isBlank(mobileNumber) || mobileNumber.length() > 10) {
            throw new RequestValidationException("Please Enter a valid mobile number");
        }
        return true;
    }
}
