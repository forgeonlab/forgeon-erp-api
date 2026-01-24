package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.OrigemMovimentacao;
import app.forgeon.forgeon_api.enums.TipoMovimentacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoEstoqueProdutoRequest {
    private Long empresaId;
    private Long produtoId;
    private TipoMovimentacao tipo;
    private Integer quantidade;
    private OrigemMovimentacao origem;
    private Long referenciaId;
}
