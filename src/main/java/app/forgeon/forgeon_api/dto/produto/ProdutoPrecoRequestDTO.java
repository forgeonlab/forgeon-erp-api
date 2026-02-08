package app.forgeon.forgeon_api.dto.produto;

import app.forgeon.forgeon_api.enums.CanalVenda;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoPrecoRequestDTO(

        @NotNull
        CanalVenda canal,

        @NotNull
        BigDecimal porcentagemLucro,

        BigDecimal porcentagemMarketplace

) {}
