package com.accountmanagement.application.mapper;

import com.accountmanagement.application.dto.AccountCreateDto;
import com.accountmanagement.application.dto.AccountDto;
import com.accountmanagement.application.dto.AccountUpdateDto;
import com.accountmanagement.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toDomain(AccountCreateDto dto);

    Account toDomain(AccountUpdateDto dto);

    @Mapping(source = "created", target = "created", qualifiedByName = "toIso")
    @Mapping(source = "updated", target = "updated", qualifiedByName = "toIso")
    AccountDto toDto(Account domain);

    @Named("toIso")
    static String toIso(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}