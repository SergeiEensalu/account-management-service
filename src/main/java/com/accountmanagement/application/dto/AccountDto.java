package com.accountmanagement.application.dto;

// Comment by S.Eensalu: Second option is to just import 'import lombok.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String created; // ISO format
    private String updated; // ISO format
}
