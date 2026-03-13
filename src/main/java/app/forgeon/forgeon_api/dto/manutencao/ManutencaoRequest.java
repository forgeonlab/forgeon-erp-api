package app.forgeon.forgeon_api.dto.manutencao;

import app.forgeon.forgeon_api.enums.TipoManutencao;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ManutencaoRequest {

    @NotNull
    private UUID impressoraPublicId;

    @NotNull
    private TipoManutencao tipo;

    private String descricao;

    private Double custo;
}
