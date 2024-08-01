package com.okta.auth.security.practice.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class PhoneNumber {
    String number;
    Type type;
    public enum Type {
        HOME,
        WORK
    }

    public PhoneNumber(String number, Type type){
        this.number = number;
        this.type = type;
    }
}



