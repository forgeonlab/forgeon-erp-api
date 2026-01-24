package app.forgeon.forgeon_api.dto.manutencao;

import app.forgeon.forgeon_api.enums.TipoManutencao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ManutencaoResponse {
    private UUID id;
    private String impressoraNome;
    private TipoManutencao tipo;
    private String descricao;
    private Double custo;
    private LocalDateTime data;
}
