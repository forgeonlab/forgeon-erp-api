package app.forgeon.forgeon_api.dto.produto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoProducaoRequestDTO(

        @NotNull
        BigDecimal horasProducao,

        @NotNull
        BigDecimal filamentoTotalGramas

) {}
