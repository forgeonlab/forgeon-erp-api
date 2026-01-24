package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.MovimentacaoFilamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoFilamentoRepository extends JpaRepository<MovimentacaoFilamento, Long> {
    List<MovimentacaoFilamento> findByEmpresaIdOrderByDataDesc(Long empresaId);
}
