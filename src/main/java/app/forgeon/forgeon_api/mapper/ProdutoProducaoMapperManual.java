package app.forgeon.forgeon_api.mapper;

import app.forgeon.forgeon_api.dto.produto.*;
import app.forgeon.forgeon_api.model.ProdutoProducao;
import org.springframework.stereotype.Component;

@Component
public class ProdutoProducaoMapperManual {

    public ProdutoProducao toEntity(ProdutoProducaoRequestDTO dto) {
        ProdutoProducao producao = new ProdutoProducao();
        producao.setHorasProducao(dto.horasProducao());
        producao.setFilamentoTotalGramas(dto.filamentoTotalGramas());
        return producao;
    }

    public ProdutoProducaoResponseDTO toResponseDTO(ProdutoProducao entity) {
        return new ProdutoProducaoResponseDTO(
                entity.getHorasProducao(),
                entity.getFilamentoTotalGramas()
        );
    }
}
