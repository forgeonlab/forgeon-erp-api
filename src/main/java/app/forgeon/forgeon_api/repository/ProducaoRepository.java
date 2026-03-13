package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProducaoRepository extends JpaRepository<Producao, UUID> {

    /* ================= LISTAGEM ================= */

    List<Producao> findByEmpresaPublicIdOrderByCriadoEmDesc(UUID empresaPublicId);

    List<Producao> findByEmpresaPublicIdAndStatus(
            UUID empresaPublicId,
            StatusProducao status
    );

    /* ================= BUSCA SEGURA ================= */

    Optional<Producao> findByPublicIdAndEmpresaPublicId(
            UUID publicId,
            UUID empresaPublicId
    );

    /* ================= CONTADORES ================= */

    long countByEmpresaPublicId(UUID empresaPublicId);

    long countByEmpresaPublicIdAndStatus(
            UUID empresaPublicId,
            StatusProducao status
    );

    /* ================= CONTROLE DE IMPRESSORA ================= */

    boolean existsByEmpresaPublicIdAndImpressora_PublicIdAndStatus(
            UUID empresaPublicId,
            UUID impressoraPublicId,
            StatusProducao status
    );
}