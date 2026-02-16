package app.forgeon.forgeon_api.dto.pedido;

import app.forgeon.forgeon_api.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoListResponseDTO(
        UUID publicId,
        StatusPedido status,
        BigDecimal valorTotal,
        LocalDateTime criadoEm
) {}
