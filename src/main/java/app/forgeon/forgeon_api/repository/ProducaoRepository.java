package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProducaoRepository extends JpaRepository<Producao, UUID> {
    List<Producao> findByEmpresaIdOrderByDataDesc(UUID empresaId);
    List<Producao> findByEmpresaIdAndStatus(UUID empresaId, StatusProducao status);
}
