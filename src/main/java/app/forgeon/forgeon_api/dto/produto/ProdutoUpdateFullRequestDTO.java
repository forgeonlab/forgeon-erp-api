package app.forgeon.forgeon_api.dto.produto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProdutoUpdateFullRequestDTO(

        @Valid
        @NotNull
        ProdutoUpdateRequestDTO produto,

        @Valid
        @NotNull
        ProdutoProducaoUpdateDTO producao,

        @Valid
        @NotNull
        List<ProdutoPrecoRequestDTO> precos
) {}
