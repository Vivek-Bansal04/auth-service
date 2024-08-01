package com.okta.auth.security.practice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankTransferService {

    @Autowired
    private BankTransferRepository bankTransferRepository;

    void createTransfer(int amount){
        Account a = new Account();
        a.setId(UUID.randomUUID().toString().substring(7));
        a.setIban("as");
        a.setFirstName("vivek");
        a.setLastName("bansal");
//        a.setPhoneNumbers();
        Account ab = new Account();
        ab.setId(UUID.randomUUID().toString().substring(7));
        ab.setIban("asdfgasdf");
        ab.setFirstName("suyash");
        ab.setLastName("yadav");

        BankTransfer b = new BankTransfer();
        b.setId(UUID.randomUUID().toString().substring(7));
        b.setReceiver(a);
        b.setState(BankTransfer.State.CREATED);
        b.setReference("zsdf");
        b.setSender(ab);
//        b.setAmount(amount);

        bankTransferRepository.save(b);


    }

}
