package com.okta.auth.security.practice.connection;

import org.springframework.stereotype.Service;

@Service
public class ExternalService {

    public void externalCall() {
        Sleep.sleep(4000);
    }
}
