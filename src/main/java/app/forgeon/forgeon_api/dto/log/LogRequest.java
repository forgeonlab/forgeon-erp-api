package app.forgeon.forgeon_api.dto.log;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LogRequest {
    private UUID empresaId;
    private UUID usuarioId;
    private String entidade;
    private UUID entidadeId;
    private String acao;
}
