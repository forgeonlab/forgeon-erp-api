package app.forgeon.forgeon_api.dto.me;

import java.util.UUID;

public record MeUsuarioDTO(
        UUID publicId,
        String nome,
        String email,
        String papel
) {}
