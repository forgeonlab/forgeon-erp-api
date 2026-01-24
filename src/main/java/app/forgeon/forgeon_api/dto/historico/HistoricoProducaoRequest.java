package app.forgeon.forgeon_api.dto.historico;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HistoricoProducaoRequest {
    private UUID producaoId;
    private StatusProducao status;
}
