package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    Optional<Empresa> findByPublicId(UUID publicId);

    boolean existsByCnpj(String cnpj);

    boolean existsByEmail(String email);
}
