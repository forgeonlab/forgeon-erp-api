package app.forgeon.forgeon_api.dto.empresa;

import lombok.Data;

import java.util.UUID;

@Data
public class ConfiguracaoEmpresaCreateDTO {
    private UUID empresaId;
    private String chave;
    private String valor;
}
