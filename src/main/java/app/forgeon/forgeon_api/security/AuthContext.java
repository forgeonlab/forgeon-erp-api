package app.forgeon.forgeon_api.security;

import java.util.UUID;

public record AuthContext(
        UUID userPublicId,
        UUID empresaPublicId,
        String role
) {}
