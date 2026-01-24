package app.forgeon.forgeon_api.dto.perda;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PerdaRequest {
    private UUID producaoId;
    private String motivo;
    private Double pesoPerdido;
}
