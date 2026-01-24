package app.forgeon.forgeon_api.dto.produto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {
    private Long empresaId;
    private String sku;
    private String nome;
    private Double precoVenda;
    private Boolean ativo;
}
