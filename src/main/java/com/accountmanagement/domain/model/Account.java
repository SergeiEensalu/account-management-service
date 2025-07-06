package com.accountmanagement.domain.model;

// Comment by S.Eensalu: Second option is to just import 'import lombok.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

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