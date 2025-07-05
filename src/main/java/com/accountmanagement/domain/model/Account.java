package com.accountmanagement.domain.model;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String name;
    private String phoneNumber;
}