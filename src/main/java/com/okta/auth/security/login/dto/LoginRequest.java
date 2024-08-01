package com.okta.auth.security.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class LoginRequest {
    private static final long serialVersionUID = -629949657640489765L;

    //otp, password
    //default OTP for now
    private String logintype;
    private String mobile;
    private String deviceid;
    private String password;
    private String otp;
}
