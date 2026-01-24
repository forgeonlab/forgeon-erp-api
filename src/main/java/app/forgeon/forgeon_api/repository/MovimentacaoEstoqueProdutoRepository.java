package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.MovimentacaoEstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoEstoqueProdutoRepository extends JpaRepository<MovimentacaoEstoqueProduto, Long> {
    List<MovimentacaoEstoqueProduto> findByEmpresaIdOrderByDataDesc(Long empresaId);
}
