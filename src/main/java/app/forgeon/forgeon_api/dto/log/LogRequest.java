package app.forgeon.forgeon_api.dto.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRequest {
    private Long empresaId;
    private Long usuarioId;
    private String entidade;
    private Long entidadeId;
    private String acao;
}
