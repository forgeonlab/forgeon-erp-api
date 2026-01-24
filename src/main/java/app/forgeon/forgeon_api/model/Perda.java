package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "perdas")
@Getter
@Setter
public class Perda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producao_id", nullable = false)
    private Producao producao;

    @Column(nullable = false)
    private String motivo;

    @Column(name = "peso_perdido", nullable = false)
    private Double pesoPerdido;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();
}
