package app.forgeon.forgeon_api.dto.impressora;

import app.forgeon.forgeon_api.enums.StatusImpressora;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ImpressoraRequest {
    private UUID empresaId;
    private String nome;
    private String modelo;
    private StatusImpressora status;
    private Boolean ativo;
}
