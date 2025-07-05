package com.accountmanagement.domain.model;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String name;
    private String phoneNumber;
    private LocalDateTime created;
    private LocalDateTime updated;
}