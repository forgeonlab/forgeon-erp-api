package app.forgeon.forgeon_api.dto.consumo;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConsumoFilamentoRequest {
    private UUID producaoId;
    private UUID filamentoId;
    private Double pesoUsado;
}
