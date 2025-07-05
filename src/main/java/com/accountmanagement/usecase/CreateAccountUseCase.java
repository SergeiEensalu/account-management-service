package com.accountmanagement.usecase;


import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.repository.AccountPort;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountUseCase {

    private final AccountPort accountPort;

    public CreateAccountUseCase(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    public Account execute(Account account) {
        return accountPort.save(account);
    }
}