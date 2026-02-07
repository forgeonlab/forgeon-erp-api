package app.forgeon.forgeon_api.dto.cliente;

public record ClienteEstatisticasDTO(
        long total,
        long ativos,
        long inativos,
        long novosEsteMes
) {}
