package app.forgeon.forgeon_api.dto.previsao;

public record PrevisaoFaturamentoDTO(
        String mesPrevisto,
        Double valorPrevisto,
        Double tendencia,
        String status // CRESCENDO | ESTAVEL | CAINDO
) {}
