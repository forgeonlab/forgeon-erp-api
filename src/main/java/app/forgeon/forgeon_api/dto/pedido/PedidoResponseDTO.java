package app.forgeon.forgeon_api.dto.pedido;

import app.forgeon.forgeon_api.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID publicId,
        StatusPedido status,
        BigDecimal valorTotal
) {}
