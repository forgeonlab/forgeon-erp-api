package app.forgeon.forgeon_api.repository.auth;

import app.forgeon.forgeon_api.model.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUsuarioId(UUID usuarioId);

}
