package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "clientes",
        indexes = {
                @Index(name = "idx_cliente_empresa", columnList = "empresa_id"),
                @Index(name = "idx_cliente_ativo", columnList = "ativo"),
                @Index(name = "idx_cliente_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // 🔗 Cada cliente pertence a uma empresa
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 120)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /* ==============================
       Ciclo de vida JPA
       ============================== */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
