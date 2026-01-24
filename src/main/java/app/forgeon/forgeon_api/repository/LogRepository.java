package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {
    List<Log> findByEmpresaOrderByDataDesc(Empresa empresa);
}
