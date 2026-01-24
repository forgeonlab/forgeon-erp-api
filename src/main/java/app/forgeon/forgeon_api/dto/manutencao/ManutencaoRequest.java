package app.forgeon.forgeon_api.dto.manutencao;

import app.forgeon.forgeon_api.enums.TipoManutencao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManutencaoRequest {
    private Long impressoraId;
    private TipoManutencao tipo;
    private String descricao;
    private Double custo;
}
