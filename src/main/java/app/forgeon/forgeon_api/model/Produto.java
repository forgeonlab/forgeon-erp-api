package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.TipoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "produtos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"empresa_public_id", "sku"})
        }
)
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "empresa_public_id", nullable = false)
    private UUID empresaPublicId;

    @Column(nullable = false, length = 100)
    private String sku;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(name = "preco_venda", nullable = false)
    private BigDecimal precoVenda;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoProduto tipo = TipoProduto.ESTOQUE;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "criado_por")
    private UUID criadoPor;

    // 🔗 Relações
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ProdutoProducao producao;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProdutoPreco> precos;

    @PrePersist
    public void prePersist() {
        this.publicId = UUID.randomUUID();
        this.criadoEm = LocalDateTime.now();
        if (this.tipo == null) {
            // Default for legacy records when tipo is omitted.
            this.tipo = TipoProduto.ESTOQUE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
