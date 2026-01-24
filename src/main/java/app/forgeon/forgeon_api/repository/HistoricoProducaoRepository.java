package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.HistoricoProducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoProducaoRepository extends JpaRepository<HistoricoProducao, Long> {
    List<HistoricoProducao> findByProducao_IdOrderByDataAsc(Long producaoId);
}
