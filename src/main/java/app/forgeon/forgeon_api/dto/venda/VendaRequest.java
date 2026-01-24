package app.forgeon.forgeon_api.dto.venda;

import app.forgeon.forgeon_api.enums.StatusVenda;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaRequest {
    private Long empresaId;
    private Long produtoId;
    private Long clienteId;
    private Integer quantidade;
    private Double precoUnitario;
    private StatusVenda status;
}
