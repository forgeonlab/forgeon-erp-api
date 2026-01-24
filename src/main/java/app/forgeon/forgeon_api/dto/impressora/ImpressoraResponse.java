package app.forgeon.forgeon_api.dto.impressora;

import app.forgeon.forgeon_api.enums.StatusImpressora;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ImpressoraResponse {
    private UUID id;
    private String nome;
    private String modelo;
    private StatusImpressora status;
    private Boolean ativo;
}
