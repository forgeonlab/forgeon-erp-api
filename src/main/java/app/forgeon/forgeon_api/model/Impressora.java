package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusImpressora;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "impressoras")
@Getter
@Setter
public class Impressora {

    /* ================= ID INTERNO ================= */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /* ================= ID EXTERNO ================= */

    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    /* ================= MULTI-TENANT ================= */

    @Column(name = "empresa_public_id", nullable = false, updatable = false)
    private UUID empresaPublicId;

    /* ================= DADOS ================= */

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatusImpressora status = StatusImpressora.DISPONIVEL;

    @Column(nullable = false)
    private Boolean ativo = true;

    /* ================= AUDITORIA ================= */

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    /* ================= CALLBACKS ================= */

    @PrePersist
    public void prePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
        criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
