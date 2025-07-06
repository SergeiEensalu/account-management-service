package com.accountmanagement.application.dto;

import jakarta.validation.constraints.Pattern;

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
public class AccountUpdateDto {

    private String name;

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number must be valid"
    )
    private String phoneNumber;
}
