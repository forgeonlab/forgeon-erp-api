package app.forgeon.forgeon_api.dto.error;

import java.time.LocalDateTime;

public record ApiErrorDTO(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {}
