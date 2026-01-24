package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.OrigemMovimentacao;
import app.forgeon.forgeon_api.enums.TipoMovimentacao;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MovimentacaoEstoqueProdutoRequest {
    private UUID empresaId;
    private UUID produtoId;
    private TipoMovimentacao tipo;
    private Integer quantidade;
    private OrigemMovimentacao origem;
    private UUID referenciaId;
}
