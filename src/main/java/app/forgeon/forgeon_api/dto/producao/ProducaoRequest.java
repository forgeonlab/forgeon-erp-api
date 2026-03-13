package app.forgeon.forgeon_api.dto.producao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProducaoRequest(

        @NotNull
        UUID produtoPublicId,

        UUID impressoraPublicId,

        @NotNull
        @Positive
        Integer quantidadePlanejada,

        LocalDateTime fimPrevisto,

        boolean reservarEstoque

) {}