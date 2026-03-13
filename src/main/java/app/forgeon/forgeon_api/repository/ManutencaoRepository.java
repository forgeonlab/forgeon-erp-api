package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, UUID> {

    List<Manutencao> findByEmpresaPublicIdOrderByDataDesc(UUID empresaPublicId);

    Optional<Manutencao> findByPublicIdAndEmpresaPublicId(
            UUID publicId,
            UUID empresaPublicId
    );
}
