package app.forgeon.forgeon_api.dto.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LogResponse {
    private UUID id;
    private String usuario;
    private String entidade;
    private UUID entidadeId;
    private String acao;
    private LocalDateTime data;
}
