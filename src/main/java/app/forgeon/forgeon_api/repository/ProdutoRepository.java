package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByEmpresaId(UUID empresaId);
    Optional<Produto> findByEmpresaIdAndSku(UUID empresaId, String sku);
}
