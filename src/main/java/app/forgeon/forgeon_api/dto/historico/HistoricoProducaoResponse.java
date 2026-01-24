package app.forgeon.forgeon_api.dto.historico;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HistoricoProducaoResponse {
    private Long id;
    private StatusProducao status;
    private LocalDateTime data;
}
