package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.EstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, UUID> {
    List<EstoqueProduto> findByEmpresaId(UUID empresaId);
    Optional<EstoqueProduto> findByEmpresaIdAndProdutoId(UUID empresaId, UUID produtoId);
}
