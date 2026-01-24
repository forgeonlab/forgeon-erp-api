package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.TipoMovimentacaoFilamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MovimentacaoFilamentoResponse {
    private Long id;
    private String filamentoNome;
    private String material;
    private String cor;
    private TipoMovimentacaoFilamento tipo;
    private Double quantidade;
    private String origem;
    private LocalDateTime data;
}
