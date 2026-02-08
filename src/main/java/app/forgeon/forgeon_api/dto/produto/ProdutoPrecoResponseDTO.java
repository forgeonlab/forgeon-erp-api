package app.forgeon.forgeon_api.dto.produto;

import app.forgeon.forgeon_api.enums.CanalVenda;
import java.math.BigDecimal;

public record ProdutoPrecoResponseDTO(

        CanalVenda canal,
        BigDecimal porcentagemLucro,
        BigDecimal porcentagemMarketplace,
        BigDecimal precoSugerido,
        BigDecimal precoFinal

) {}
