package app.forgeon.forgeon_api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoUpdateRequestDTO(

        @NotBlank
        String nome,

        @NotNull
        BigDecimal precoVenda,

        @NotNull
        Boolean ativo
) {}
