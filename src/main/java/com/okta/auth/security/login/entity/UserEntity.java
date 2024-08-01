package com.okta.auth.security.login.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.persistence.Transient;

@Entity(name = "user_entity")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String mobile;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}