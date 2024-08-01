package com.okta.auth.security.practice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    default Account findByIdOrThrow(String id) {
        return findById(id).orElseThrow();
    }
}
