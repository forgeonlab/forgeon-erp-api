package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {
    List<Venda> findByEmpresa(Empresa empresa);
    List<Venda> findByStatus(String status);

    @Query("""
        SELECT new app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO(
            p.id,
            p.nome,
            SUM(v.quantidade),
            SUM(v.valorTotal)
        )
        FROM Venda v
        JOIN v.produto p
        WHERE v.empresa.id = :empresaId
          AND v.status = app.forgeon.forgeon_api.enums.StatusVenda.CONCLUIDA
        GROUP BY p.id, p.nome
        ORDER BY SUM(v.quantidade) DESC
    """)
    List<ProdutoVendaDTO> vendasPorProduto(@Param("empresaId") UUID empresaId);
}
