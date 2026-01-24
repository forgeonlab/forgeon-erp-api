package app.forgeon.forgeon_api.dto.cliente;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private Long empresaId;
    private String nome;
    private String email;
    private String telefone;
    private Boolean ativo;
}
