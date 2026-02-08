package app.forgeon.forgeon_api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoCreateRequestDTO(
        @NotBlank
        @Size(max = 100)
        String sku,

        @NotBlank
        @Size(max = 255)
        String nome,

        @NotNull
        BigDecimal precoVenda
) {}
