package com.accountmanagement.domain.repository;

import com.accountmanagement.domain.model.Account;

import java.util.Optional;
import java.util.List;

public interface AccountPort {
    Account save(Account account);

    Optional<Account> findById(Long id);

    List<Account> findAll();

    void deleteById(Long id);
}