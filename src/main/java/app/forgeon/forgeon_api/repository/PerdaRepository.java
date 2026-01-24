package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Perda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerdaRepository extends JpaRepository<Perda, Long> {
    List<Perda> findByProducao_IdOrderByDataDesc(Long producaoId);
}
