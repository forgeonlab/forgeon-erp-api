package app.forgeon.forgeon_api.dto.producao;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProducaoRequest {
    private Long empresaId;
    private Long produtoId;
    private Long impressoraId;
    private Integer quantidadePlanejada;
    private Integer quantidadeBoa;
    private StatusProducao status;
    private LocalDateTime inicio;
    private LocalDateTime fimPrevisto;
}
