package com.accountmanagement.domain.model;

// Comment by S.Eensalu: Second option is to just import 'import lombok.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

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