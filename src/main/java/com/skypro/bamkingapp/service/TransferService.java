package com.skypro.bamkingapp.service;

import com.skypro.bamkingapp.dto.request.TransferRequest;
import com.skypro.bamkingapp.exception.InvalidTransferException;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final AccountService accountService;

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void transferMoney(TransferRequest transferRequest) {
        if (accountService.getAccount(transferRequest.userTo(), transferRequest.accountTo())
                .equals(accountService.getAccount(transferRequest.userFrom(), transferRequest.accountFrom()))) {
            accountService.changeBalance(transferRequest.userFrom(),
                    transferRequest.accountFrom(),
                    Operation.WITHDRAW,
                    transferRequest.amount());
            accountService.changeBalance(transferRequest.userTo(),
                    transferRequest.accountTo(),
                    Operation.DEPOSIT,
                    transferRequest.amount());
        } else {
            throw new InvalidTransferException();
        }
    }
}
