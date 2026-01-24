package app.forgeon.forgeon_api.dto.consumo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsumoFilamentoResponse {
    private Long id;
    private String producaoDescricao;
    private String filamentoNome;
    private Double pesoUsado;
}
