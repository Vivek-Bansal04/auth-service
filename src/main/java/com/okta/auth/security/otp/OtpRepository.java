package com.okta.auth.security.otp;

import com.okta.auth.security.otp.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Otp findOtpByIdentifier(String identifier);
}
