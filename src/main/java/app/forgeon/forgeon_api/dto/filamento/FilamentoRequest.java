package app.forgeon.forgeon_api.dto.filamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilamentoRequest {
    private Long empresaId;
    private String sku;
    private String marca;
    private String material;
    private String cor;
    private Double pesoInicial;
    private Double pesoAtual;
    private Double custoRolo;
    private Boolean ativo;
}
