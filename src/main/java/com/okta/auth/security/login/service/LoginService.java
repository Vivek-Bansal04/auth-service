package com.okta.auth.security.login.service;

import com.okta.auth.security.login.dto.LoginRequest;
import com.okta.auth.security.login.dto.LoginResponse;
import com.okta.auth.security.login.entity.UserEntity;

public interface LoginService {
    LoginResponse loginUser(LoginRequest request) throws Exception;

    UserEntity registerUser(LoginRequest request);
}
