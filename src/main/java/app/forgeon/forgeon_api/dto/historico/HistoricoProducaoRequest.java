package app.forgeon.forgeon_api.dto.historico;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoProducaoRequest {
    private Long producaoId;
    private StatusProducao status;
}
