package app.forgeon.forgeon_api.dto.estoque;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AjusteEstoqueRequestDTO(

        @NotNull
        UUID produtoPublicId,

        @NotNull
        Integer quantidadeFinal,

        String referencia
) {}
