package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "estoques",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"produto_id"})
        }
)
@Getter
@Setter
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade = 0;

    @Column(nullable = false)
    private Integer reservado = 0;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
