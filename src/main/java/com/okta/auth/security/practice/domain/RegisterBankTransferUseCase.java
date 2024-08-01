package com.okta.auth.security.practice.domain;

import org.springframework.stereotype.Service;

@Service
public class RegisterBankTransferUseCase {
    private final AccountRepository accountRepository;
    private final BankTransferRepository bankTransferRepository;

    public RegisterBankTransferUseCase(AccountRepository accountRepository, BankTransferRepository bankTransferRepository) {
        this.accountRepository = accountRepository;
        this.bankTransferRepository = bankTransferRepository;
    }

    public void execute(String bankTransferId, String reference, String senderId, String receiverId, int amount) {
        Account sender = accountRepository.findByIdOrThrow(senderId);
        Account receiver = accountRepository.findByIdOrThrow(receiverId);

        BankTransfer bankTransfer = new BankTransfer(bankTransferId, reference, sender, receiver, amount);
        bankTransferRepository.save(bankTransfer);
    }
}
