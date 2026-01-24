package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produtos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"empresa_id", "sku"})
})
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(nullable = false, length = 100)
    private String sku;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(name = "preco_venda", nullable = false)
    private Double precoVenda;

    @Column(nullable = false)
    private Boolean ativo = true;
}
