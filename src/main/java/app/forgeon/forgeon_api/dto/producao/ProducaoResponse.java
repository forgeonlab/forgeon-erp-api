package app.forgeon.forgeon_api.dto.producao;

import app.forgeon.forgeon_api.enums.StatusProducao;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProducaoResponse(

        UUID publicId,

        String produtoNome,
        UUID produtoPublicId,

        String impressoraNome,
        UUID impressoraPublicId,

        Integer quantidadePlanejada,
        Integer quantidadeBoa,

        StatusProducao status,

        LocalDateTime inicio,
        LocalDateTime fimPrevisto,
        LocalDateTime fimReal,

        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm

) {}