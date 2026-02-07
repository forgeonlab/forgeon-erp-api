package app.forgeon.forgeon_api.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoResponse {

    private UUID id; // publicId
    private String sku;
    private String nome;
    private BigDecimal precoVenda;
    private Boolean ativo;

    public static ProdutoResponse fromEntity(app.forgeon.forgeon_api.model.Produto produto) {
        return new ProdutoResponse(
                produto.getPublicId(),
                produto.getSku(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getAtivo()
        );
    }
}
