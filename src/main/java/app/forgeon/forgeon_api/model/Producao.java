package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusProducao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "producoes")
@Getter
@Setter
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "impressora_id")
    private Impressora impressora;

    @Column
    private LocalDateTime inicio;

    @Column(name = "fim_previsto")
    private LocalDateTime fimPrevisto;

    @Column(name = "quantidade_planejada", nullable = false)
    private Integer quantidadePlanejada;

    @Column(name = "quantidade_boa", nullable = false)
    private Integer quantidadeBoa = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProducao status = StatusProducao.PLANEJADA;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();
}
