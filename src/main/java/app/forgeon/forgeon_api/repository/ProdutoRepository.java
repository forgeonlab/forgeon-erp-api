package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    boolean existsByEmpresaPublicIdAndSku(UUID empresaPublicId, String sku);

    Optional<Produto> findByPublicId(UUID publicId);

    Optional<Produto> findByPublicIdAndEmpresaPublicId(
            UUID publicId,
            UUID empresaPublicId
    );

    List<Produto> findAllByEmpresaPublicId(UUID empresaPublicId);
}