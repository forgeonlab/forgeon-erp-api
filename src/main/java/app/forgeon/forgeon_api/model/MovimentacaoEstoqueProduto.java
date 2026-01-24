package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.OrigemMovimentacao;
import app.forgeon.forgeon_api.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentacao_estoque_produto")
@Getter
@Setter
public class MovimentacaoEstoqueProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "empresa_id", nullable = false)
    private UUID empresaId;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrigemMovimentacao origem;

    @Column(name = "referencia_id")
    private UUID referenciaId;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();
}
