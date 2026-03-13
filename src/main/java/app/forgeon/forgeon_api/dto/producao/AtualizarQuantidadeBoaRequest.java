package app.forgeon.forgeon_api.dto.producao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AtualizarQuantidadeBoaRequest(

        @NotNull
        @PositiveOrZero
        Integer quantidadeBoa

) {}