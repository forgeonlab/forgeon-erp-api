package app.forgeon.forgeon_api.dto.empresa;

import lombok.Data;

import java.util.UUID;

@Data
public class ConfiguracaoEmpresaDTO {
    private UUID id;
    private UUID empresaId;
    private String chave;
    private String valor;
}
