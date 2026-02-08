package app.forgeon.forgeon_api.dto.estoque;

import java.util.UUID;

public record EstoqueResponseDTO(
        UUID produtoPublicId,
        String sku,
        String nomeProduto,
        Integer quantidade,
        Integer reservado,
        Integer disponivel
) {}
