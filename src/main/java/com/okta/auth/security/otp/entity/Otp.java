package com.okta.auth.security.otp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class Otp implements Serializable {

    private static final long serialVersionUID = -2343244333434432341L;

    @Id
    @GeneratedValue
    private long id;

    private String identifier;

    private String value;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
