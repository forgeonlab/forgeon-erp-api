package app.forgeon.forgeon_api.dto.produto;

import java.math.BigDecimal;

public record ProdutoProducaoResponseDTO(

        BigDecimal horasProducao,
        BigDecimal filamentoTotalGramas

) {}
