package com.accountmanagement.infrastructure.persistence.jpa;

import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.repository.AccountPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountPersistenceAdapter implements AccountPort {

    private final AccountJpaRepository jpaRepository;

    public AccountPersistenceAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account create(Account account) {
        AccountEntity entity = toEntity(account);
        AccountEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Account update(Account account) {
        AccountEntity existing = jpaRepository.findById(account.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existing.setName(account.getName());
        existing.setPhoneNumber(account.getPhoneNumber());

        AccountEntity updated = jpaRepository.save(existing);
        return toDomain(updated);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    public Optional<Account> findByPhoneNumber(String phoneNumber) {
        return jpaRepository.findByPhoneNumber(phoneNumber)
                .map(this::toDomain);
    }

    private Account toDomain(AccountEntity entity) {
        return Account.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    private AccountEntity toEntity(Account domain) {
        return AccountEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .phoneNumber(domain.getPhoneNumber())
                .build();
    }
}