package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.OrigemMovimentacao;
import app.forgeon.forgeon_api.enums.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MovimentacaoEstoqueProdutoResponse {
    private Long id;
    private String produtoNome;
    private TipoMovimentacao tipo;
    private OrigemMovimentacao origem;
    private Integer quantidade;
    private LocalDateTime data;
}
