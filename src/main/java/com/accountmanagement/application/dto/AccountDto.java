package com.accountmanagement.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String created;
    private String updated;
}
