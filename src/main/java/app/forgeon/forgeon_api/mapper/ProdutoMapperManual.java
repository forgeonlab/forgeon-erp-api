package app.forgeon.forgeon_api.mapper;

import app.forgeon.forgeon_api.dto.produto.*;
import app.forgeon.forgeon_api.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapperManual {

    public Produto toEntity(ProdutoCreateRequestDTO dto) {
        Produto produto = new Produto();
        produto.setSku(dto.sku());
        produto.setNome(dto.nome());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setTipo(dto.tipo());
        produto.setAtivo(true);
        return produto;
    }

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getPublicId(),
                produto.getSku(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getTipo(),
                produto.getAtivo(),
                produto.getCriadoEm()
        );
    }
}
