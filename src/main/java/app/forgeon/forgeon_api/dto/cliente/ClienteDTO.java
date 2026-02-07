package app.forgeon.forgeon_api.dto.cliente;

import app.forgeon.forgeon_api.model.Cliente;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClienteDTO(
        UUID id,
        String nome,
        String email,
        String telefone,
        Boolean ativo,
        LocalDateTime createdAt
) {
    public static ClienteDTO fromEntity(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getAtivo(),
                cliente.getCreatedAt()
        );
    }
}
