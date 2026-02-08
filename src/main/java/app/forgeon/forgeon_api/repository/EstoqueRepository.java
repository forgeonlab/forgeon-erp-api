package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Estoque;
import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {

    Optional<Estoque> findByProduto(Produto produto);

    @Query("""
    select count(e)
    from Estoque e
    where e.quantidade = 0
""")
    Long countSemEstoque();

    @Query("""
    select count(e)
    from Estoque e
    where e.reservado > 0
""")
    Long countComReserva();

}
