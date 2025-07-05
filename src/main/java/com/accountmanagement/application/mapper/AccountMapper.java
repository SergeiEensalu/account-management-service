package com.accountmanagement.application.mapper;

import com.accountmanagement.application.dto.AccountCreateDto;
import com.accountmanagement.application.dto.AccountDto;
import com.accountmanagement.application.dto.AccountUpdateDto;
import com.accountmanagement.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toDomain(AccountCreateDto dto);

    Account toDomain(AccountUpdateDto dto);

    AccountDto toDto(Account domain);
}