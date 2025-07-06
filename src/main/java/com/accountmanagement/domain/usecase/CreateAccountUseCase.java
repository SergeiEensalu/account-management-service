package com.accountmanagement.domain.usecase;

import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountUseCase {

    private final AccountService accountService;

    public CreateAccountUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account execute(Account account) {
        return accountService.createAccount(account);
    }
}