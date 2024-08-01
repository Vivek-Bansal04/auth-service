package com.okta.auth.security.practice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransferRepository extends JpaRepository<BankTransfer, String> {
    default BankTransfer findByIdOrThrow(String id) {
        return findById(id).orElseThrow();
    }

    List<BankTransfer> findBySenderId(String senderId);
}
