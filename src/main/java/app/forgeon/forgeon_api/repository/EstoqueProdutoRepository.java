package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.EstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Long> {
    List<EstoqueProduto> findByEmpresaId(Long empresaId);
    Optional<EstoqueProduto> findByEmpresaIdAndProdutoId(Long empresaId, Long produtoId);
}
