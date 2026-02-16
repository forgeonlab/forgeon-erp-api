package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusOrdemProducao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ordens_producao")
@Getter
@Setter
public class OrdemProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID empresaPublicId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdemProducao status;

    @Column(name = "iniciado_em")
    private LocalDateTime iniciadoEm;

    @Column(name = "finalizado_em")
    private LocalDateTime finalizadoEm;

    @PrePersist
    public void prePersist() {
        this.status = StatusOrdemProducao.AGUARDANDO;
    }
}