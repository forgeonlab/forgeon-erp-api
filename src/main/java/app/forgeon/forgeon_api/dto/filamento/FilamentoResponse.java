package app.forgeon.forgeon_api.dto.filamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FilamentoResponse {
    private UUID id;
    private String sku;
    private String marca;
    private String material;
    private String cor;
    private Double pesoAtual;
    private Double pesoInicial;
    private Double custoRolo;
    private Boolean ativo;
}
