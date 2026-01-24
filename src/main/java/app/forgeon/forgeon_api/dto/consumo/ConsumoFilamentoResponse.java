package app.forgeon.forgeon_api.dto.consumo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ConsumoFilamentoResponse {
    private UUID id;
    private String producaoDescricao;
    private String filamentoNome;
    private Double pesoUsado;
}
