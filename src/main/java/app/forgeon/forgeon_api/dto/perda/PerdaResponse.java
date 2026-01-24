package app.forgeon.forgeon_api.dto.perda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PerdaResponse {
    private Long id;
    private String producaoNome;
    private String motivo;
    private Double pesoPerdido;
    private LocalDateTime data;
}
