package app.forgeon.forgeon_api.dto.venda;

import app.forgeon.forgeon_api.enums.StatusVenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class VendaResponse {
    private Long id;
    private String cliente;
    private String produto;
    private Integer quantidade;
    private Double valorTotal;
    private StatusVenda status;
    private LocalDateTime data;
}
