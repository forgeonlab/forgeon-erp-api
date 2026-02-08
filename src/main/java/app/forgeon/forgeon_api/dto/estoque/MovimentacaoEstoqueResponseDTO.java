package app.forgeon.forgeon_api.dto.estoque;

import app.forgeon.forgeon_api.enums.TipoMovimentacaoEstoque;

import java.time.LocalDateTime;

public record MovimentacaoEstoqueResponseDTO(
        TipoMovimentacaoEstoque tipo,
        Integer quantidade,
        String referencia,
        LocalDateTime criadoEm
) {}
