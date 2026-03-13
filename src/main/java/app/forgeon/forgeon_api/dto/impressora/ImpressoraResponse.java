package app.forgeon.forgeon_api.dto.impressora;

import app.forgeon.forgeon_api.enums.StatusImpressora;

import java.util.UUID;

public record ImpressoraResponse(
        UUID publicId,
        String nome,
        String modelo,
        StatusImpressora status,
        Boolean ativo
) {}
