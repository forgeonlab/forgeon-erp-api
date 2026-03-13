package app.forgeon.forgeon_api.dto.filamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilamentoRequest {
    @NotBlank(message = "sku e obrigatorio")
    private String sku;
    @NotBlank(message = "marca e obrigatoria")
    private String marca;
    @NotBlank(message = "material e obrigatorio")
    private String material;
    @NotBlank(message = "cor e obrigatoria")
    private String cor;
    @NotNull(message = "pesoInicial e obrigatorio")
    @Positive(message = "pesoInicial deve ser maior que zero")
    private Double pesoInicial;
    @PositiveOrZero(message = "pesoAtual deve ser zero ou maior")
    private Double pesoAtual;
    @NotNull(message = "custoRolo e obrigatorio")
    @Positive(message = "custoRolo deve ser maior que zero")
    private Double custoRolo;
    private Boolean ativo;
}
