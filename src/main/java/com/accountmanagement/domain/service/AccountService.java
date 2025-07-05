package com.accountmanagement.domain.service;

import com.accountmanagement.domain.exception.AccountAlreadyExistsException;
import com.accountmanagement.domain.exception.AccountNotFoundException;
import com.accountmanagement.domain.exception.NothingToUpdateException;
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

        return accountPort.create(account);
    }

    public Account getById(Long id) {
        return accountPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));
    }

    public void deleteAccount(Long id) {
        accountPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));

        accountPort.deleteById(id);
    }

    public Account updateAccount(Long id, Account updatedAccount) {

        // Comment by S.Eensalu: Before updating, make sure the account exists
        Account existing = accountPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));

        boolean isSameName = updatedAccount.getName() == null || updatedAccount.getName().equals(existing.getName());
        boolean isSamePhone = updatedAccount.getPhoneNumber() == null || updatedAccount.getPhoneNumber().equals(existing.getPhoneNumber());

        if (isSameName && isSamePhone) {
            // Comment by S.Eensalu: Nothing changed — return error
            throw new NothingToUpdateException("No changes provided for update");
        }

        if (!isSameName) {
            existing.setName(updatedAccount.getName());
        }

        if (!isSamePhone) {
            // Comment by S.Eensalu: To show different implementation options, I implement this product in the way,
            // that phone number is unique (yes, i can use database for unique definition, but let's also try this way),
            // but here I check if new phone number is already taken

            accountPort.findByPhoneNumber(updatedAccount.getPhoneNumber())
                    .ifPresent(existingAccount -> {
                        if (!existingAccount.getId().equals(id)) {
                            throw new AccountAlreadyExistsException("Phone number already in use");
                        }
                    });

            existing.setPhoneNumber(updatedAccount.getPhoneNumber());
        }

        return accountPort.update(existing);
    }
}