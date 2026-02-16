package app.forgeon.forgeon_api.dto.pedido;

import java.util.UUID;

public record ItemPedidoRequestDTO(
        UUID produtoPublicId,
        Integer quantidade
) {}