package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Impressora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressoraRepository extends JpaRepository<Impressora, Long> {
    List<Impressora> findByEmpresaId(Long empresaId);
}
