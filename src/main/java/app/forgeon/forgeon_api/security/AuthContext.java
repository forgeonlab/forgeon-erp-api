package app.forgeon.forgeon_api.security;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AuthContext {

    public final UUID usuarioPublicId;
    public final UUID empresaPublicId;
    private final String role;

    public AuthContext(UUID usuarioPublicId, UUID empresaPublicId, String role) {
        this.usuarioPublicId = usuarioPublicId;
        this.empresaPublicId = empresaPublicId;
        this.role = role;
    }

    public UUID getUsuarioPublicId() {
        return usuarioPublicId;
    }

    public UUID getEmpresaPublicId() {
        return empresaPublicId;
    }

    public String getRole() {
        return role;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
}
