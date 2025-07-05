package com.accountmanagement.usecase;

import com.accountmanagement.domain.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class DeleteAccountUseCase {

    private final AccountService accountService;

    public DeleteAccountUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    public void execute(Long id) {
        accountService.deleteAccount(id);
    }
}