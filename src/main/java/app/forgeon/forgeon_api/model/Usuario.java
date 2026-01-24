package app.forgeon.forgeon_api.model;

import app.forgeon.forgeon_api.enums.PapelUsuario;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_usuario_empresa")
    )
    private Empresa empresa;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelUsuario papel;

    @Column(nullable = false)
    private boolean ativo = true;
}
