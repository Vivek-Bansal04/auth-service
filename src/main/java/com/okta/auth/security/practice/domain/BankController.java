package com.okta.auth.security.practice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;


@RestController
public class BankController {
    private BankTransferService bankTransferService;
    private RegisterBankTransferUseCase service;

    @Autowired
    public BankController(
            BankTransferService bankTransferService,
            RegisterBankTransferUseCase service
    ){
        this.bankTransferService = bankTransferService;
        this.service = service;
    }

    @PostMapping("/transfer")
    void createTransfer() {
        bankTransferService.createTransfer(100);
    }

    @PostMapping(path = "/execute")
    void execute(
            @PathVariable(value = "bankId") String bankTransferId,
                 @PathVariable(value = "senderId") String senderId,
            @PathVariable(value = "receiverId") String receiverId
    ) {
        service.execute(bankTransferId,"zsdf",senderId,receiverId,1000);
    }
}
