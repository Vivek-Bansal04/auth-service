package com.okta.auth.security.otp;

import com.okta.auth.security.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/generate")
    public String generate(@RequestParam String identifier) {
        return otpService.generateOtp(identifier);
    }
}
