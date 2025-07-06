package com.accountmanagement.application.controller;

import com.accountmanagement.application.dto.AccountCreateDto;
import com.accountmanagement.application.dto.AccountDto;
import com.accountmanagement.application.dto.AccountUpdateDto;
import com.accountmanagement.application.mapper.AccountMapper;
import com.accountmanagement.application.response.ApiResponse;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.usecase.CreateAccountUseCase;
import com.accountmanagement.domain.usecase.DeleteAccountUseCase;
import com.accountmanagement.domain.usecase.GetAccountByIdUseCase;
import com.accountmanagement.domain.usecase.UpdateAccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Comment by S.Eensalu: Second option is to just import 'import org.springframework.web.bind.annotation.*';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Accounts", description = "Operations related to account management")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final AccountMapper accountMapper;

    public AccountController(
            CreateAccountUseCase createAccountUseCase,
            GetAccountByIdUseCase getAccountByIdUseCase,
            UpdateAccountUseCase updateAccountUseCase,
            DeleteAccountUseCase deleteAccountUseCase,
            AccountMapper accountMapper
    ) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
        this.accountMapper = accountMapper;
    }

    @PostMapping("/accounts")
    @Operation(
            summary = "Create a new account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Account created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<AccountDto>> createAccount(@Valid @RequestBody AccountCreateDto dto) {
        Account domain = accountMapper.toDomain(dto);
        Account created = createAccountUseCase.execute(domain);
        // Comment by S.Eensalu: Unified response format.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Account created successfully", accountMapper.toDto(created)));
    }

    @GetMapping("/accounts/{id}")
    @Operation(
            summary = "Get account by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Account retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    )
            }
    )
    public ResponseEntity<ApiResponse<AccountDto>> getAccountById(@PathVariable Long id) {
        Account account = getAccountByIdUseCase.execute(id);
        // Comment by S.Eensalu: Unified response format.
        return ResponseEntity.ok(ApiResponse.success("Account retrieved successfully", accountMapper.toDto(account)));
    }

    @PutMapping("/accounts/{id}")
    @Operation(
            summary = "Update account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Account updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Validation failed",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    )
            }
    )
    public ResponseEntity<ApiResponse<AccountDto>> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountUpdateDto dto
    ) {
        Account domain = accountMapper.toDomain(dto);
        Account updated = updateAccountUseCase.execute(id, domain);
        // Comment by S.Eensalu: Unified response format.
        return ResponseEntity.ok(ApiResponse.success("Account updated successfully", accountMapper.toDto(updated)));
    }

    @DeleteMapping("/accounts/{id}")
    @Operation(
            summary = "Delete account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Account deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    )
            }
    )
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id) {
        deleteAccountUseCase.execute(id);
        // Comment by S.Eensalu: Unified response format.
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully", null));
    }
}
