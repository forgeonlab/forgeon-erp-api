package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    List<ItemPedido> findAllByPedidoId(UUID pedidoId);
}
