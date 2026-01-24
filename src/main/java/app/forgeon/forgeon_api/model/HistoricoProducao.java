package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusProducao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historico_producao")
@Getter
@Setter
public class HistoricoProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "producao_id", nullable = false)
    private Producao producao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProducao status;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();
}
