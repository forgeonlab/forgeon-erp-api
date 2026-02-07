package app.forgeon.forgeon_api.dto.alerta;

public record AlertaDTO(
        String codigo,
        String tipo, // INFO | WARNING | CRITICAL
        String mensagem,
        Double valorAtual,
        Double valorReferencia
) {}
