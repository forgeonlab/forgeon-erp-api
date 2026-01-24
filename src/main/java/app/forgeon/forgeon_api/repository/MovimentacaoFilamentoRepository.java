package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.MovimentacaoFilamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimentacaoFilamentoRepository extends JpaRepository<MovimentacaoFilamento, UUID> {
    List<MovimentacaoFilamento> findByEmpresaIdOrderByDataDesc(UUID empresaId);
}
