package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "configuracoes_empresa",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_empresa_chave",
                        columnNames = {"empresa_id", "chave"}
                )
        }
)
@Getter
@Setter
public class ConfiguracaoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_config_empresa")
    )
    private Empresa empresa;

    @Column(nullable = false, length = 100)
    private String chave;

    @Column(nullable = false, length = 500)
    private String valor;
}
