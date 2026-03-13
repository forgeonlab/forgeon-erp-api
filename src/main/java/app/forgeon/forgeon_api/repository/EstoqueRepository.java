package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.dto.estoque.EstoqueListaDTO;
import app.forgeon.forgeon_api.model.Estoque;
import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("""
SELECT new app.forgeon.forgeon_api.dto.estoque.EstoqueListaDTO(
    p.publicId,
    p.sku,
    p.nome,
    e.quantidade,
    e.reservado,
    (e.quantidade - e.reservado)
)
FROM Estoque e
JOIN e.produto p
WHERE p.empresaPublicId = :empresaPublicId
ORDER BY p.nome
""")
    List<EstoqueListaDTO> listar(UUID empresaPublicId);
}
