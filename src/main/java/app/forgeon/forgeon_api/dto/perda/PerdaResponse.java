package app.forgeon.forgeon_api.dto.perda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PerdaResponse {
    private UUID id;
    private String producaoNome;
    private String motivo;
    private Double pesoPerdido;
    private LocalDateTime data;
}
