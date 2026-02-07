package app.forgeon.forgeon_api.dto.auth;

public record LoginResponseDTO(
        String accessToken,
        String refreshToken,
        long expiresIn
) {}
