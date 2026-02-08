package app.forgeon.forgeon_api.dto.estoque;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservaEstoqueRequestDTO(
        @NotNull UUID produtoPublicId,
        @NotNull @Min(1) Integer quantidade,
        String referencia
) {}
