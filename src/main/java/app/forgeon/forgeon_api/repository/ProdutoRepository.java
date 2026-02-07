package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    /* =========================
       CONSULTAS PRINCIPAIS
    ========================= */

    // Lista todos os produtos ativos da empresa
    List<Produto> findByEmpresaPublicIdAndAtivoTrue(UUID empresaPublicId);

    // Lista paginada (ideal para telas grandes)
    Page<Produto> findByEmpresaPublicIdAndAtivoTrue(
            UUID empresaPublicId,
            Pageable pageable
    );

    // Busca segura por produto (evita acesso entre empresas)
    Optional<Produto> findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
            UUID publicId,
            UUID empresaPublicId
    );

    /* =========================
       BUSCAS / FILTROS
    ========================= */

    // Busca por nome (search)
    List<Produto> findByEmpresaPublicIdAndNomeContainingIgnoreCaseAndAtivoTrue(
            UUID empresaPublicId,
            String nome
    );

    // Busca paginada por nome
    Page<Produto> findByEmpresaPublicIdAndNomeContainingIgnoreCaseAndAtivoTrue(
            UUID empresaPublicId,
            String nome,
            Pageable pageable
    );

    /* =========================
       VALIDAÇÕES
    ========================= */

    // Valida SKU duplicado (CREATE)
    boolean existsByEmpresaPublicIdAndSku(
            UUID empresaPublicId,
            String sku
    );

    // Valida SKU duplicado (UPDATE)
    boolean existsByEmpresaPublicIdAndSkuAndPublicIdNot(
            UUID empresaPublicId,
            String sku,
            UUID publicId
    );
}
