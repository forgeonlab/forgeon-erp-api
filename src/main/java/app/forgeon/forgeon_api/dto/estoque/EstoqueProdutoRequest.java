package app.forgeon.forgeon_api.dto.estoque;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EstoqueProdutoRequest {
    private UUID empresaId;
    private UUID produtoId;
    private Integer quantidade;
}
