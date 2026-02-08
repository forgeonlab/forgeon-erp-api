package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.CanalVenda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "produto_precos")
@Getter
@Setter
public class ProdutoPreco {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CanalVenda canal;

    @Column(name = "porcentagem_lucro", nullable = false)
    private BigDecimal porcentagemLucro;

    @Column(name = "porcentagem_marketplace")
    private BigDecimal porcentagemMarketplace;

    @Column(name = "preco_sugerido", nullable = false)
    private BigDecimal precoSugerido;

    @Column(name = "preco_final", nullable = false)
    private BigDecimal precoFinal;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
