package com.okta.auth.security.login.controller;

import com.okta.auth.security.login.dto.LoginRequest;
import com.okta.auth.security.login.dto.LoginResponse;
import com.okta.auth.security.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(
            LoginService loginService
    ){
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> registerEntity(@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(loginService.loginUser(request));
    }

    @GetMapping("/about")
    public ResponseEntity<String> about(){
        return ResponseEntity.ok("allowed");
    }
}