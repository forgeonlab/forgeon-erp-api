package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.ConsumoFilamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumoFilamentoRepository extends JpaRepository<ConsumoFilamento, Long> {
    List<ConsumoFilamento> findByProducao_Id(Long producaoId);
    List<ConsumoFilamento> findByFilamento_Id(Long filamentoId);
}
