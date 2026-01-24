package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "consumo_filamento")
@Getter
@Setter
public class ConsumoFilamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "producao_id", nullable = false)
    private Producao producao;

    @ManyToOne
    @JoinColumn(name = "filamento_id", nullable = false)
    private Filamento filamento;

    @Column(name = "peso_usado", nullable = false)
    private Double pesoUsado;
}
