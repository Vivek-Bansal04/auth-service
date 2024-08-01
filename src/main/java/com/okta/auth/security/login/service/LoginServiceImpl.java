package com.okta.auth.security.login.service;

import com.okta.auth.security.JWTUtil;
import com.okta.auth.security.login.UserRepository.UserRepository;
import com.okta.auth.security.login.dto.LoginRequest;
import com.okta.auth.security.login.dto.LoginResponse;
import com.okta.auth.security.login.entity.UserEntity;
import com.okta.auth.security.otp.service.OtpService;
import com.okta.auth.security.utils.exceptions.UnAuthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class LoginServiceImpl implements LoginService{
    private OtpService otpService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passEncoder;

    private JWTUtil jwtService;

    @Autowired
    public LoginServiceImpl(
            OtpService otpService,
            UserRepository userRepository,
            BCryptPasswordEncoder passEncoder,
            JWTUtil jwtService
    ){
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.passEncoder = passEncoder;
        this.jwtService = jwtService;
    }

    //currently supported for otp only will add password support as well
    @Override
    @Transactional
    public LoginResponse loginUser(LoginRequest request){
        if(Objects.isNull(request) || !verifyOTP(request,true)){
            throw new UnAuthorizedException("User not Authenticated");
        }
        UserEntity user = null;
        user = userRepository.findUserEntityByMobile(request.getMobile());
        if(Objects.isNull(user)){
            user = registerUser(request);
        }
        var token = jwtService.generateToken(request.getMobile());
        var response = LoginResponse.builder().token(token).id(user.getId()).build();

        return response;
    }

    @Override
    public UserEntity registerUser(LoginRequest request) {
        UserEntity user = new UserEntity();
        user.setMobile(request.getMobile());
        String passCode = StringUtils.isNotBlank(request.getPassword()) ? request.getPassword() : request.getOtp();
        user.setPassword(getEncryptedPassword(request.getMobile(),passCode));
        return userRepository.save(user);
    }

    private boolean verifyOTP(LoginRequest loginRequest,boolean remove) {
        if (StringUtils.isNoneBlank(loginRequest.getMobile(), loginRequest.getOtp())) {
            return otpService.verifyOTP(loginRequest.getMobile(), loginRequest.getOtp(),remove);
        }
        return false;
    }

    public boolean isUserRegistered(String mobile){
        return userRepository.existsUserEntityByMobile(mobile);
    }

    public String getEncryptedPassword(String mobile, String password) {
        return passEncoder.encode(mobile + "_" + password);
    }

    public boolean passwordMatch(String mobile, String password, String encryptedPassword) {
        return passEncoder.matches(mobile + "_" + password, encryptedPassword);
    }
}
