package app.forgeon.forgeon_api.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expirationMillis;

    private Key signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /* =========================
       GERA TOKEN
    ========================= */
    public String gerarToken(
            UUID userPublicId,
            UUID empresaPublicId,
            String role
    ) {
        return Jwts.builder()
                .setSubject(userPublicId.toString())
                .claim("empresa", empresaPublicId.toString())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expirationMillis)
                )
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* =========================
       VALIDA E DEVOLVE CLAIMS  ✅ (ISSO FALTAVA)
    ========================= */
    public Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* =========================
       HELPERS (opcional)
    ========================= */
    public boolean isValid(String token) {
        try {
            validarToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public UUID extractUserPublicId(String token) {
        return UUID.fromString(validarToken(token).getSubject());
    }

    public UUID extractEmpresaPublicId(String token) {
        return UUID.fromString(
                validarToken(token).get("empresa", String.class)
        );
    }

    public String extractRole(String token) {
        return validarToken(token).get("role", String.class);
    }
}
