package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    List<Cliente> findByEmpresaId(UUID empresaId);

    List<Cliente> findByEmpresaIdAndAtivoTrue(UUID empresaId);
}
