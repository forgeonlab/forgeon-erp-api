package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Impressora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImpressoraRepository extends JpaRepository<Impressora, UUID> {

    List<Impressora> findByEmpresaPublicId(UUID empresaPublicId);

    Optional<Impressora> findByPublicIdAndEmpresaPublicId(
            UUID publicId,
            UUID empresaPublicId
    );

}
