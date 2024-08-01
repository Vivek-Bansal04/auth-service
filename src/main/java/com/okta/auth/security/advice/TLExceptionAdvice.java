package com.okta.auth.security.advice;

import com.okta.auth.security.utils.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice("com.okta.auth.security")
public class TLExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            Exception.class
    })
    public final ResponseEntity<Object> handleExceptions(Exception ex) {
        log.error("", ex);

        final ResponseBuilder<Object> output = parseUnknownException(ex);

        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseBuilder<Object> parseUnknownException(Exception ex) {
        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        output.setErrorData(ex.getCause());
        return output;
    }


}
