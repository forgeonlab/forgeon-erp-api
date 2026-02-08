package app.forgeon.forgeon_api.dto.produto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProdutoCreateFullRequestDTO(

        @Valid
        @NotNull
        ProdutoCreateRequestDTO produto,

        @Valid
        @NotNull
        ProdutoProducaoRequestDTO producao,

        @Valid
        @NotNull
        List<ProdutoPrecoRequestDTO> precos

) {}
