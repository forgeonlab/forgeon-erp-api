package app.forgeon.forgeon_api.dto.producao;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ProducaoRequest {
    private UUID empresaId;
    private UUID produtoId;
    private UUID impressoraId;
    private Integer quantidadePlanejada;
    private Integer quantidadeBoa;
    private StatusProducao status;
    private LocalDateTime inicio;
    private LocalDateTime fimPrevisto;
}
