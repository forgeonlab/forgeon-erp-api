package app.forgeon.forgeon_api.security;

import app.forgeon.forgeon_api.config.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {

    private SecurityUtils() {}

    public static UUID getEmpresaId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        // Ajuste conforme seu JWT
        var principal = authentication.getPrincipal();

        if (principal instanceof JwtUserDetails jwtUser) {
            return jwtUser.getEmpresaId();
        }

        throw new IllegalStateException("JWT inválido");
    }
}
