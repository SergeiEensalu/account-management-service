package com.accountmanagement.domain.service;

import com.accountmanagement.domain.exception.AccountAlreadyExistsException;
import com.accountmanagement.domain.exception.AccountNotFoundException;
import com.accountmanagement.domain.exception.NothingToUpdateException;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.repository.AccountPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountPort accountPort;

    public AccountService(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    public Account createAccount(Account account) {
        if (account.getPhoneNumber() != null) {
            validatePhoneNumberUniqueness(account.getPhoneNumber(), null);
        }

        return accountPort.create(account);
    }

    public Account getById(Long id) {
        return findById(id).orElseThrow(() ->
                new AccountNotFoundException("Account with ID " + id + " not found"));
    }

    public void deleteAccount(Long id) {
        getById(id);
        accountPort.deleteById(id);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        Account existing = getById(id); // Comment by S.Eensalu: Before updating, make sure the account exists

        boolean isSameName = updatedAccount.getName() == null || updatedAccount.getName().equals(existing.getName());
        boolean isSamePhone = updatedAccount.getPhoneNumber() == null || updatedAccount.getPhoneNumber().equals(existing.getPhoneNumber());

        if (isSameName && isSamePhone) {
            throw new NothingToUpdateException("No changes provided for update");
        }

        if (!isSameName) {
            existing.setName(updatedAccount.getName());
        }

        if (!isSamePhone) {
            validatePhoneNumberUniqueness(updatedAccount.getPhoneNumber(), id);
            existing.setPhoneNumber(updatedAccount.getPhoneNumber());
        }

        return accountPort.update(existing);
    }

    private Optional<Account> findById(Long id) {
        return accountPort.findById(id);
    }

    private Optional<Account> findByPhoneNumber(String phoneNumber) {
        return accountPort.findByPhoneNumber(phoneNumber);
    }

    /**
     * Comment by S.Eensalu
     * Validates that phone number is not used by another account (except allowedId).
     * <p>
     * Business rule:
     * To support correct "nimemakse" (proxy payment) functionality,
     * the phone number (if provided) must be unique.
     * Otherwise, it would be ambiguous to determine which account to pay.
     */
    private void validatePhoneNumberUniqueness(String phoneNumber, Long allowedId) {
        findByPhoneNumber(phoneNumber).ifPresent(existing -> {
            if (!existing.getId().equals(allowedId)) {
                throw new AccountAlreadyExistsException("Phone number already in use");
            }
        });
    }
}