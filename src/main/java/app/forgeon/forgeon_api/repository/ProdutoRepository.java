package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByEmpresaId(Long empresaId);
    Optional<Produto> findByEmpresaIdAndSku(Long empresaId, String sku);
}
