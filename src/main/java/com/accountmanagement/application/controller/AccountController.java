package com.accountmanagement.application.controller;

import com.accountmanagement.application.dto.AccountDto;
import com.accountmanagement.application.mapper.AccountMapper;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.usecase.CreateAccountUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final AccountMapper accountMapper;

    public AccountController(CreateAccountUseCase createAccountUseCase, AccountMapper accountMapper) {
        this.createAccountUseCase = createAccountUseCase;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto dto) {
        Account domain = accountMapper.toDomain(dto);
        Account created = createAccountUseCase.execute(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toDto(created));
    }
}