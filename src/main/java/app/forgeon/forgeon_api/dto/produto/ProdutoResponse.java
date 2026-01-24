package app.forgeon.forgeon_api.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoResponse {
    private UUID id;
    private String sku;
    private String nome;
    private Double precoVenda;
    private Boolean ativo;
}
