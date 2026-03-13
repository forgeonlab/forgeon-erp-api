package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Filamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilamentoRepository extends JpaRepository<Filamento, UUID> {
    List<Filamento> findByEmpresaIdAndAtivoTrue(UUID empresaId);
    Optional<Filamento> findByEmpresaIdAndSku(UUID empresaId, String sku);
    boolean existsByEmpresaIdAndSku(UUID empresaId, String sku);
}
