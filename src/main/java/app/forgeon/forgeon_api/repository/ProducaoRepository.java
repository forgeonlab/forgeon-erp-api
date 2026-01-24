package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducaoRepository extends JpaRepository<Producao, Long> {
    List<Producao> findByEmpresaIdOrderByDataDesc(Long empresaId);
    List<Producao> findByEmpresaIdAndStatus(Long empresaId, StatusProducao status);
}
