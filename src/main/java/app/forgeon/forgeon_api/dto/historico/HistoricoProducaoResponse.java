package app.forgeon.forgeon_api.dto.historico;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class HistoricoProducaoResponse {
    private UUID id;
    private StatusProducao status;
    private LocalDateTime data;
}
