package app.forgeon.forgeon_api.dto.perda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerdaRequest {
    private Long producaoId;
    private String motivo;
    private Double pesoPerdido;
}
