package app.forgeon.forgeon_api.model.auth;

import app.forgeon.forgeon_api.model.Usuario;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Instant createdAt;

    protected RefreshToken() {
        // JPA
    }

    public RefreshToken(Usuario usuario, String token) {
        this.usuario = usuario;
        this.token = token;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
