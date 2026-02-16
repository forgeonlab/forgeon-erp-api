package app.forgeon.forgeon_api.dto.ordemproducao;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrdemProducaoFilaDTO(
        UUID ordemId,
        UUID pedidoPublicId,
        String produtoNome,
        Integer quantidade,
        String status,
        LocalDateTime criadoEm
) {}