package app.forgeon.forgeon_api.dto.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LogResponse {
    private Long id;
    private String usuario;
    private String entidade;
    private Long entidadeId;
    private String acao;
    private LocalDateTime data;
}
