package app.forgeon.forgeon_api.dto.venda;

import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.model.Venda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class VendaResponse {

    private UUID id;
    private UUID produtoPublicId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal total;
    private StatusVenda status;

    public static VendaResponse fromEntity(Venda venda) {
        return new VendaResponse(
                venda.getPublicId(),
                venda.getProdutoPublicId(),
                venda.getProdutoNome(),
                venda.getQuantidade(),
                venda.getPrecoUnitario(),
                venda.getTotal(),
                venda.getStatus()
        );
    }
}
