package app.forgeon.forgeon_api.dto.producao;

import app.forgeon.forgeon_api.enums.StatusProducao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProducaoResponse {
    private UUID id;
    private String produtoNome;
    private String impressoraNome;
    private Integer quantidadePlanejada;
    private Integer quantidadeBoa;
    private StatusProducao status;
    private LocalDateTime inicio;
    private LocalDateTime fimPrevisto;
    private LocalDateTime data;
}
