package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.StatusImpressora;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "impressoras")
@Getter
@Setter
public class Impressora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatusImpressora status = StatusImpressora.DISPONIVEL;

    @Column(nullable = false)
    private Boolean ativo = true;
}
