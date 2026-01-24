package app.forgeon.forgeon_api.dto.venda;

import app.forgeon.forgeon_api.enums.StatusVenda;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VendaRequest {
    private UUID empresaId;
    private UUID produtoId;
    private UUID clienteId;
    private Integer quantidade;
    private Double precoUnitario;
    private StatusVenda status;
}
