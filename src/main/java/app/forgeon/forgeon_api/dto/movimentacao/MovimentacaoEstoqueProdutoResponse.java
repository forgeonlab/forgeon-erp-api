package app.forgeon.forgeon_api.dto.movimentacao;

import app.forgeon.forgeon_api.enums.OrigemMovimentacao;
import app.forgeon.forgeon_api.enums.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MovimentacaoEstoqueProdutoResponse {
    private UUID id;
    private String produtoNome;
    private TipoMovimentacao tipo;
    private OrigemMovimentacao origem;
    private Integer quantidade;
    private LocalDateTime data;
}
