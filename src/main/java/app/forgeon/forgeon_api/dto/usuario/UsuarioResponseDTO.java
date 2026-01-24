package app.forgeon.forgeon_api.dto.usuario;

import app.forgeon.forgeon_api.model.Usuario;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID publicId,
        String nome,
        String email,
        String papel,
        boolean ativo
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getPublicId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel().name(),
                usuario.isAtivo()
        );
    }
}
