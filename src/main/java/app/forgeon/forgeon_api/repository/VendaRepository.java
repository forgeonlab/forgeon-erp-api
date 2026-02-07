package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {

    /* =========================
       CONSULTAS PRINCIPAIS
    ========================= */

    List<Venda> findByEmpresaPublicIdAndAtivoTrue(UUID empresaPublicId);

    Optional<Venda> findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
            UUID publicId,
            UUID empresaPublicId
    );

    /* =========================
       DASHBOARD / ANALYTICS
    ========================= */

    @Query("""
        SELECT new app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO(
            v.produtoPublicId,
            v.produtoNome,
            SUM(v.quantidade),
            SUM(v.total)
        )
        FROM Venda v
        WHERE v.empresaPublicId = :empresaPublicId
          AND v.status = app.forgeon.forgeon_api.enums.StatusVenda.CONCLUIDA
          AND v.ativo = true
        GROUP BY v.produtoPublicId, v.produtoNome
        ORDER BY SUM(v.quantidade) DESC
    """)
    List<ProdutoVendaDTO> vendasPorProduto(
            @Param("empresaPublicId") UUID empresaPublicId
    );

    /* =========================
       CONSULTAS AUXILIARES
    ========================= */

    long countByEmpresaPublicIdAndStatusAndAtivoTrue(
            UUID empresaPublicId,
            StatusVenda status
    );
}
