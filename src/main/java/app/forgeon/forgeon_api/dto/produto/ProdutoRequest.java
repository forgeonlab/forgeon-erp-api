package app.forgeon.forgeon_api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @NotBlank(message = "SKU é obrigatório")
    private String sku;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "Preço de venda deve ser maior que zero")
    private BigDecimal precoVenda;

    // Opcional: só se você quiser permitir ativar/desativar na criação/edição
    private Boolean ativo;
}
