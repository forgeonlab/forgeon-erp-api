package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Filamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilamentoRepository extends JpaRepository<Filamento, Long> {
    List<Filamento> findByEmpresaId(Long empresaId);
    Optional<Filamento> findByEmpresaIdAndSku(Long empresaId, String sku);
}
