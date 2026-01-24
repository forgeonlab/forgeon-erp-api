package app.forgeon.forgeon_api.dto.consumo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumoFilamentoRequest {
    private Long producaoId;
    private Long filamentoId;
    private Double pesoUsado;
}
