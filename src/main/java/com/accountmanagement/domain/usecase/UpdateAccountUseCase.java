package com.accountmanagement.domain.usecase;

import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAccountUseCase {
    private final AccountService accountService;

    public UpdateAccountUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account execute(Long id, Account updatedAccount) {
        return accountService.updateAccount(id, updatedAccount);
    }
}
