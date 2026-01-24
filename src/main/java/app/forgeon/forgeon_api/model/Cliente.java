package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 relacionamento N:1 — cada cliente pertence a uma empresa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 120)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;
}
