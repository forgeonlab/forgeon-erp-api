package app.forgeon.forgeon_api.dto.produto;

import java.util.List;

public record ProdutoDetalhadoResponseDTO(

        ProdutoResponseDTO produto,
        ProdutoProducaoResponseDTO producao,
        List<ProdutoPrecoResponseDTO> precos

) {}
