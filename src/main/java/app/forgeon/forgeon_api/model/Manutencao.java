package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.TipoManutencao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "manutencoes")
@Getter
@Setter
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "impressora_id", nullable = false)
    private Impressora impressora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private TipoManutencao tipo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column
    private Double custo;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();
}
