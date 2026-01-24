package app.forgeon.forgeon_api.dto.cliente;

import lombok.Data;

import java.util.UUID;

@Data
public class ClienteDTO {
    private UUID id;
    private UUID empresaId;
    private String nome;
    private String email;
    private String telefone;
    private Boolean ativo;
}
