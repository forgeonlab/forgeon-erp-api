package app.forgeon.forgeon_api.service.auth;

import app.forgeon.forgeon_api.model.Usuario;
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

    @Value("${security.jwt.access-expiration}")
    private long accessExpirationMillis;

    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpirationMillis;

    private Key signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /* =========================
       ACCESS TOKEN
    ========================= */
    public String gerarAccessToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getPublicId().toString())
                .claim("empresa", usuario.getEmpresa().getPublicId().toString())
//                .claim("role", usuario.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessExpirationMillis)
                )
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* =========================
       REFRESH TOKEN
       (menos claims, mais longo)
    ========================= */
    public String gerarRefreshToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getPublicId().toString())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + refreshExpirationMillis)
                )
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* =========================
       VALIDA TOKEN
    ========================= */
    public Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* =========================
       HELPERS
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

    /* =========================
       EXPIRAÇÃO (FRONTEND)
    ========================= */
    public long getAccessTokenExpiration() {
        return accessExpirationMillis / 1000; // em segundos
    }
}
