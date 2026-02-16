package app.forgeon.forgeon_api.dto.pedido;

import java.util.List;

public record CriarPedidoRequestDTO(
        List<ItemPedidoRequestDTO> itens,
        String cupom // opcional
) {}
