package com.okta.auth.security.utils;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBuilder<T> {
    private int status;
    private String message;
    private T data;
    private String error;
    private Object errorData;
    private String requestId;
}
