package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusProducao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "producoes")
@Getter
@Setter
public class Producao {

    /* ================= IDENTIDADE INTERNA ================= */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /* ================= IDENTIDADE EXTERNA ================= */

    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    /* ================= MULTI-TENANT ================= */

    @Column(name = "empresa_public_id", nullable = false, updatable = false)
    private UUID empresaPublicId;

    /* ================= RELAÇÕES ================= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "impressora_id")
    private Impressora impressora;

    /* ================= CONTROLE DE PRODUÇÃO ================= */

    @Column(name = "inicio")
    private LocalDateTime inicio;

    @Column(name = "fim_previsto")
    private LocalDateTime fimPrevisto;

    @Column(name = "fim_real")
    private LocalDateTime fimReal;

    @Column(name = "quantidade_planejada", nullable = false)
    private Integer quantidadePlanejada;

    @Column(name = "quantidade_boa", nullable = false)
    private Integer quantidadeBoa = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProducao status = StatusProducao.PLANEJADA;

    /* ================= AUDITORIA ================= */

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "criado_por", nullable = false, updatable = false)
    private UUID criadoPor;

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