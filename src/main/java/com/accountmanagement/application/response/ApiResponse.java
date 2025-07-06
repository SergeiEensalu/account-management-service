package com.accountmanagement.application.response;

// Comment by S.Eensalu: Second option is to just import 'import lombok.*;';
// but for better readability and conflicts prevention i prefer to import exactly what I use, not more not less.
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "ApiResponse", description = "Unified API response wrapper")
public class ApiResponse<T> {
    private int status;
    private String message;
    @Schema(description = "Response payload")
    private T data;
    private List<String> errors;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message, List<String> errors) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
}