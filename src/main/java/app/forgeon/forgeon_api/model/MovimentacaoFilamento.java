package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.TipoMovimentacaoFilamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentacao_filamento")
@Getter
@Setter
public class MovimentacaoFilamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "empresa_id", nullable = false)
    private UUID empresaId;

    @ManyToOne
    @JoinColumn(name = "filamento_id", nullable = false)
    private Filamento filamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimentacaoFilamento tipo;

    @Column(nullable = false)
    private Double quantidade;

    @Column(length = 50)
    private String origem;

    @Column(name = "referencia_id")
    private UUID referenciaId;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();
}
