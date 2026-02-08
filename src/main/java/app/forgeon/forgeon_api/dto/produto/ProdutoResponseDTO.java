package app.forgeon.forgeon_api.dto.produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProdutoResponseDTO(

        UUID publicId,
        String sku,
        String nome,
        BigDecimal precoVenda,
        Boolean ativo,
        LocalDateTime criadoEm

) {}
