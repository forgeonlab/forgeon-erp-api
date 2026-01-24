package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.TipoMovimentacaoFilamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoFilamentoRequest {
    private Long empresaId;
    private Long filamentoId;
    private TipoMovimentacaoFilamento tipo;
    private Double quantidade;
    private String origem;
    private Long referenciaId;
}
