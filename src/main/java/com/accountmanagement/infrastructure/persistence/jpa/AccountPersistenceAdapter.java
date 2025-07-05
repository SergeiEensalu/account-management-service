package com.accountmanagement.infrastructure.persistence.jpa;

import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.repository.AccountPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountPersistenceAdapter implements AccountPort {

    private final AccountJpaRepository jpaRepository;

    public AccountPersistenceAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = toEntity(account);
        AccountEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
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