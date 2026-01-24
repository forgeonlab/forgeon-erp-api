package app.forgeon.forgeon_api.dto.impressora;

import app.forgeon.forgeon_api.enums.StatusImpressora;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImpressoraRequest {
    private Long empresaId;
    private String nome;
    private String modelo;
    private StatusImpressora status;
    private Boolean ativo;
}
