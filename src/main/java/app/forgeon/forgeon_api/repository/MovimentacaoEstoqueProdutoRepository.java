package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.MovimentacaoEstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimentacaoEstoqueProdutoRepository extends JpaRepository<MovimentacaoEstoqueProduto, UUID> {
    List<MovimentacaoEstoqueProduto> findByEmpresaIdOrderByDataDesc(UUID empresaId);
}
