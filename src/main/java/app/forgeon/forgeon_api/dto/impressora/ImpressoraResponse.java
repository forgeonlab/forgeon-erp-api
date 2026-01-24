package app.forgeon.forgeon_api.dto.impressora;

import app.forgeon.forgeon_api.enums.StatusImpressora;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImpressoraResponse {
    private Long id;
    private String nome;
    private String modelo;
    private StatusImpressora status;
    private Boolean ativo;
}
