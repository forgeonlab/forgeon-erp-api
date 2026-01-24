package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "empresas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_empresa_cnpj", columnNames = "cnpj")
        }
)
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @CNPJ
    @Column(nullable = false, length = 14)
    private String cnpj;

    @Email
    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false)
    private boolean ativa = true;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
}
