package com.accountmanagement.application.controller;

import com.accountmanagement.application.dto.AccountCreateDto;
import com.accountmanagement.application.dto.AccountDto;
import com.accountmanagement.application.mapper.AccountMapper;
import com.accountmanagement.domain.model.Account;
import com.accountmanagement.domain.usecase.CreateAccountUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AccountController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
        }
)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAccountUseCase createAccountUseCase;

    @MockBean
    private AccountMapper accountMapper;

    @Test
    void shouldCreateAccountSuccessfully() throws Exception {
        String name = "Martin Villig";
        String phone = "+37255550000";
        AccountCreateDto request = AccountCreateDto.builder()
                .name(name)
                .phoneNumber(phone)
                .build();

        Account domain = Account.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Account created = Account.builder()
                .id(1L)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        AccountDto dto = AccountDto.builder()
                .id(created.getId())
                .name(created.getName())
                .phoneNumber(created.getPhoneNumber())
                .build();

        when(accountMapper.toDomain(any(AccountCreateDto.class))).thenReturn(domain);
        when(createAccountUseCase.execute(any())).thenReturn(created);
        when(accountMapper.toDto(any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Account created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.data.phoneNumber").value(phone));
    }


    @Test
    void shouldReturn400WhenPhoneNumberMissing() throws Exception {
        String name = "Alar Karis";
        String phone = "555INVALID";

        AccountCreateDto request = AccountCreateDto.builder()
                .name(name)
                .phoneNumber(phone)
                .build();

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}