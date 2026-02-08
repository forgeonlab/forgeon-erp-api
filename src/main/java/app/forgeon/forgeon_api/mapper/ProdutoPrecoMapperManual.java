package app.forgeon.forgeon_api.mapper;

import app.forgeon.forgeon_api.dto.produto.*;
import app.forgeon.forgeon_api.model.ProdutoPreco;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPrecoMapperManual {

    public ProdutoPreco toEntity(ProdutoPrecoRequestDTO dto) {
        ProdutoPreco preco = new ProdutoPreco();
        preco.setCanal(dto.canal());
        preco.setPorcentagemLucro(dto.porcentagemLucro());
        preco.setPorcentagemMarketplace(dto.porcentagemMarketplace());
        return preco;
    }

    public ProdutoPrecoResponseDTO toResponseDTO(ProdutoPreco entity) {
        return new ProdutoPrecoResponseDTO(
                entity.getCanal(),
                entity.getPorcentagemLucro(),
                entity.getPorcentagemMarketplace(),
                entity.getPrecoSugerido(),
                entity.getPrecoFinal()
        );
    }
}
