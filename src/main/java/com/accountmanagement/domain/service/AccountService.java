package com.accountmanagement.domain.service;

import com.accountmanagement.domain.exception.AccountAlreadyExistsException;
import com.accountmanagement.domain.exception.AccountNotFoundException;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.repository.AccountPort;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountPort accountPort;

    public AccountService(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    public Account createAccount(Account account) {
        if (account.getPhoneNumber() != null &&
                accountPort.findByPhoneNumber(account.getPhoneNumber()).isPresent()) {
            throw new AccountAlreadyExistsException("Account with phone number already exists");
        }

        return accountPort.save(account);
    }

    public Account getById(Long id) {
        return accountPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        Account existing = accountPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));

        existing.setName(updatedAccount.getName());
        existing.setPhoneNumber(updatedAccount.getPhoneNumber());

        return accountPort.save(existing);
    }
}