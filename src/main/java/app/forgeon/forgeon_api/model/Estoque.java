package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "estoques",
        uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id"}))
@Getter
@Setter
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade = 0;

    @Column(nullable = false)
    private Integer reservado = 0;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public int quantidadeDisponivel() {
        return quantidade - reservado;
    }

    public void reservar(int qtd) {
        if (quantidadeDisponivel() < qtd) {
            throw new RuntimeException("Estoque insuficiente");
        }
        reservado += qtd;
    }

    public void darSaida(int qtd) {
        if (reservado < qtd) {
            throw new RuntimeException("Reserva insuficiente");
        }
        reservado -= qtd;
        quantidade -= qtd;
    }

    public void cancelarReserva(int qtd) {
        if (reservado < qtd) {
            throw new RuntimeException("Reserva inválida");
        }
        reservado -= qtd;
    }
}