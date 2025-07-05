package com.accountmanagement.domain.repository;

import com.accountmanagement.domain.model.Account;

import java.util.Optional;

public interface AccountPort {
    Account save(Account account);

    Optional<Account> findById(Long id);

    void deleteById(Long id);

    Optional<Account> findByPhoneNumber(String phoneNumber);
}