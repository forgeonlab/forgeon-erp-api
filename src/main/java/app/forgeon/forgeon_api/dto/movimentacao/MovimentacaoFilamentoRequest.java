package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.TipoMovimentacaoFilamento;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MovimentacaoFilamentoRequest {
    private UUID empresaId;
    private UUID filamentoId;
    private TipoMovimentacaoFilamento tipo;
    private Double quantidade;
    private String origem;
    private UUID referenciaId;
}
