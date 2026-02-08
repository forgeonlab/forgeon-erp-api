package app.forgeon.forgeon_api.dto.estoque;

public record EstoqueDashboardDTO(
        Long totalProdutos,
        Long produtosSemEstoque,
        Long produtosComReserva
) {}