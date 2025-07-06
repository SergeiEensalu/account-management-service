package com.accountmanagement.domain.usecase;

import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class GetAccountByIdUseCase {

    private final AccountService accountService;

    public GetAccountByIdUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account execute(Long id) {
        return accountService.getById(id);
    }
}