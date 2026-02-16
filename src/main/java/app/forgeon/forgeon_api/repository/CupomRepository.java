package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CupomRepository extends JpaRepository<Cupom, UUID> {

    Optional<Cupom> findByCodigoAndAtivoTrue(String codigo);
}
