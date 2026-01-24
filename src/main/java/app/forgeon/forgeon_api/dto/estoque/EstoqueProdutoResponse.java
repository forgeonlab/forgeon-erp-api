package app.forgeon.forgeon_api.dto.estoque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EstoqueProdutoResponse {
    private UUID id;
    private String produtoNome;
    private String produtoSku;
    private Integer quantidade;
}
