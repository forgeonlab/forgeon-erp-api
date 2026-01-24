package app.forgeon.forgeon_api.dto.estoque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstoqueProdutoResponse {
    private Long id;
    private String produtoNome;
    private String produtoSku;
    private Integer quantidade;
}
