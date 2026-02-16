package app.forgeon.forgeon_api.dto.pedido;

import app.forgeon.forgeon_api.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoDetalhadoResponseDTO(
        UUID publicId,
        StatusPedido status,
        BigDecimal valorTotal,
        BigDecimal valorDesconto,
        String cupom,
        LocalDateTime criadoEm,
        List<ItemPedidoResponseDTO> itens
) {}
