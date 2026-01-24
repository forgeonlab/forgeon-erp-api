package app.forgeon.forgeon_api.dto.empresa;

import lombok.Data;

@Data
public class ConfiguracaoEmpresaDTO {
    private Long id;
    private Long empresaId;
    private String chave;
    private String valor;
}
