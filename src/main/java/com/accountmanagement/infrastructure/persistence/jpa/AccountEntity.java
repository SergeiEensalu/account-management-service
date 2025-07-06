package com.accountmanagement.infrastructure.persistence.jpa;

// Comment by S.Eensalu: Second option is to just import 'import jakarta.persistence.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

// Comment by S.Eensalu: Second option is to just import 'import lombok.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phoneNumber")
})
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