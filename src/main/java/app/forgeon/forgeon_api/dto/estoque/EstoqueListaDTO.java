package app.forgeon.forgeon_api.dto.estoque;

import java.util.UUID;

public record EstoqueListaDTO(
        UUID produtoPublicId,
        String sku,
        String produtoNome,
        Integer quantidade,
        Integer reservado,
        Integer disponivel
) {}