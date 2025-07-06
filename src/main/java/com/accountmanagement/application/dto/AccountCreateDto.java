package com.accountmanagement.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// Comment by S.Eensalu: Second option is to just import 'import lombok.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


/**
 * Comment by S.Eensalu:
 * For the purposes of this demo project, the Account model uses simplified flat fields:
 * - name: as a single string
 * - phoneNumber: raw string
 *
 * In a real-world system, a more robust schema would be used to support internationalization,
 * data normalization, and extensibility.
 *
 * Example of more structured request format:
 *
 * {
 *   "name": {
 *     "firstName": "Sergei",
 *     "lastName": "Eensalu"
 *   },
 *   "phone": {
 *     "countryCode": "+372",
 *     "nationalNumber": "56575305",
 *     "isoCountry": "EE"
 *   }
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number must be valid"
    )
    private String phoneNumber;
}