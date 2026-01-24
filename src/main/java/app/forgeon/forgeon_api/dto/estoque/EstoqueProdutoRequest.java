package app.forgeon.forgeon_api.dto.estoque;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueProdutoRequest {
    private Long empresaId;
    private Long produtoId;
    private Integer quantidade;
}
