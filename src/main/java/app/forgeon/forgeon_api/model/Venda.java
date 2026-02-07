package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusVenda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vendas")
@Getter
@Setter
public class Venda {

    /* =========================
       IDS
    ========================= */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; // interno DB

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "empresa_public_id", nullable = false)
    private UUID empresaPublicId;

    @Column(name = "produto_public_id", nullable = false)
    private UUID produtoPublicId;

    /* =========================
       SNAPSHOT DO PRODUTO
    ========================= */

    @Column(name = "produto_nome", nullable = false, length = 255)
    private String produtoNome;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal total;

    /* =========================
       STATUS
    ========================= */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVenda status;

    /* =========================
       CONTROLE
    ========================= */

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "criado_por")
    private UUID criadoPor;
}
