package com.accountmanagement.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    public void onCreate() {
        created = LocalDateTime.now();
        updated = created;
    }

    @PreUpdate
    public void onUpdate() {
        updated = LocalDateTime.now();
    }
}