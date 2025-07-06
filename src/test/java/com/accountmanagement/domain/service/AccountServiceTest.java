package com.accountmanagement.domain.service;

import com.accountmanagement.domain.exception.AccountAlreadyExistsException;
import com.accountmanagement.domain.exception.AccountNotFoundException;
import com.accountmanagement.domain.exception.NothingToUpdateException;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.repository.AccountPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountPort accountPort;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        String name = "Martin Villig";
        String phoneNumber = "+37256575305";

        Account input = Account.builder().name(name).phoneNumber(phoneNumber).build();
        when(accountPort.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        when(accountPort.create(any())).thenReturn(input);

        Account createdAccount = accountService.createAccount(input);

        assertEquals(name, createdAccount.getName());
        verify(accountPort).create(input);
    }

    @Test
    void shouldThrowWhenPhoneNumberAlreadyExists() {
        String name = "Erki Kilu";
        String phoneNumber = "+37258560357";

        Account input = Account.builder().name(name).phoneNumber(phoneNumber).build();
        when(accountPort.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(Account.builder().id(1L).build()));

        assertThrows(AccountAlreadyExistsException.class, () -> accountService.createAccount(input));
    }

    @Test
    void shouldReturnAccountById() {
        String name = "Alar Karis";

        Account account = Account.builder().id(1L).name(name).build();
        when(accountPort.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getById(1L);

        assertEquals(name, result.getName());
    }

    @Test
    void shouldThrowIfAccountNotFound() {
        when(accountPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getById(1L));
    }

    @Test
    void shouldThrowIfNothingChanged() {
        String name = "Ursula von der Leyen";
        String phoneNumber = "+372585604444";

        Account existing = Account.builder().id(1L).name(name).phoneNumber(phoneNumber).build();
        Account input = Account.builder().name(name).phoneNumber(phoneNumber).build();

        when(accountPort.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(NothingToUpdateException.class, () -> accountService.updateAccount(1L, input));
    }

    @Test
    void shouldUpdatePhoneNumber() {
        String name = "Kalevipoeg";
        String phoneNumberCurrent = "+372585633341";
        String phoneNumberUpdated = "+372566566733";

        Account existing = Account.builder().id(1L).name(name).phoneNumber(phoneNumberCurrent).build();
        Account updated = Account.builder().name(name).phoneNumber(phoneNumberUpdated).build();

        when(accountPort.findById(1L)).thenReturn(Optional.of(existing));
        when(accountPort.findByPhoneNumber(phoneNumberUpdated)).thenReturn(Optional.empty());
        when(accountPort.update(any())).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.updateAccount(1L, updated);

        assertEquals(phoneNumberUpdated, result.getPhoneNumber());
    }

    @Test
    void shouldDeleteAccount() {
        String name = "Kaja Kallas";

        Account existing = Account.builder().id(1L).name(name).build();
        when(accountPort.findById(1L)).thenReturn(Optional.of(existing));

        accountService.deleteAccount(1L);

        verify(accountPort).deleteById(1L);
    }
}