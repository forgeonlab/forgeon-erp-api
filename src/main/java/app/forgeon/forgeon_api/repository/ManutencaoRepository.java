package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByImpressora_EmpresaIdOrderByDataDesc(Long empresaId);
}
