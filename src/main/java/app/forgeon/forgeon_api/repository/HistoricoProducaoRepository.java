package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.HistoricoProducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoProducaoRepository extends JpaRepository<HistoricoProducao, UUID> {
    List<HistoricoProducao> findByProducao_IdOrderByDataAsc(UUID producaoId);
}
