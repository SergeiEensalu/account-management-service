package com.accountmanagement.application.controller;

import com.accountmanagement.application.dto.AccountDto;
import com.accountmanagement.application.mapper.AccountMapper;
import com.accountmanagement.application.response.ApiResponse;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.usecase.CreateAccountUseCase;
import com.accountmanagement.usecase.GetAccountByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final AccountMapper accountMapper;

    public AccountController(
            CreateAccountUseCase createAccountUseCase,
            GetAccountByIdUseCase getAccountByIdUseCase,
            AccountMapper accountMapper
    ) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.accountMapper = accountMapper;
    }

    @PostMapping("/accounts")
    public ResponseEntity<ApiResponse<AccountDto>> createAccount(@Valid @RequestBody AccountDto dto) {
        Account domain = accountMapper.toDomain(dto);
        Account created = createAccountUseCase.execute(domain);

        // Comment by S.Eensalu: Unified response format.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Account created successfully", accountMapper.toDto(created)));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccountById(@PathVariable Long id) {
        Account account = getAccountByIdUseCase.execute(id);

        // Comment by S.Eensalu: Unified response format.
        return ResponseEntity
                .ok(ApiResponse.success("Account retrieved successfully", accountMapper.toDto(account)));
    }
}