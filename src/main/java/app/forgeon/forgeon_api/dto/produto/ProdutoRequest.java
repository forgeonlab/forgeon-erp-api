package app.forgeon.forgeon_api.dto.produto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProdutoRequest {
    private UUID empresaId;
    private String sku;
    private String nome;
    private Double precoVenda;
    private Boolean ativo;
}
