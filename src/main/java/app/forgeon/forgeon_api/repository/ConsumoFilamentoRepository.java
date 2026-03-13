package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.ConsumoFilamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsumoFilamentoRepository extends JpaRepository<ConsumoFilamento, UUID> {
    List<ConsumoFilamento> findByProducao_Id(UUID producaoId);
    List<ConsumoFilamento> findByFilamento_Id(UUID filamentoId);
    boolean existsByFilamento_Id(UUID filamentoId);
}
