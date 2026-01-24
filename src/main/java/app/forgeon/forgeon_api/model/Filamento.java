package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "filamentos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"empresa_id", "sku"})
)
@Getter
@Setter
public class Filamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "empresa_id", nullable = false)
    private UUID empresaId;

    @Column(nullable = false, length = 100)
    private String sku;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, length = 50)
    private String material;

    @Column(nullable = false, length = 50)
    private String cor;

    @Column(name = "peso_inicial", nullable = false)
    private Double pesoInicial;

    @Column(name = "peso_atual", nullable = false)
    private Double pesoAtual;

    @Column(name = "custo_rolo", nullable = false)
    private Double custoRolo;

    @Column(nullable = false)
    private Boolean ativo = true;
}
