package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    Page<Pedido> findAllByEmpresaPublicId(UUID empresaPublicId, Pageable pageable);

    Optional<Pedido> findByPublicIdAndEmpresaPublicId(UUID publicId, UUID empresaPublicId);
}
