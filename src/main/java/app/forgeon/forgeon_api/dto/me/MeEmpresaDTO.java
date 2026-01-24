package app.forgeon.forgeon_api.dto.me;

import java.util.UUID;

public record MeEmpresaDTO(
        UUID publicId,
        String nome
) {}
