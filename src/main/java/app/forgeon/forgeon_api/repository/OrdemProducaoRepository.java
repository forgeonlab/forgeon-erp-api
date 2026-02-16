package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.enums.StatusOrdemProducao;
import app.forgeon.forgeon_api.model.OrdemProducao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, UUID> {


    List<OrdemProducao> findAllByEmpresaPublicId(UUID empresaPublicId);

    // 🔍 Buscar ordem pelo pedido
    boolean existsByPedidoPublicId(UUID pedidoPublicId);

    // ✅ Verificar se já existe ordem para o pedido
    boolean existsByPedido_PublicId(UUID pedidoPublicId);

    // 📋 Fila completa da empresa
    List<OrdemProducao> findAllByEmpresaPublicIdOrderByIdAsc(UUID empresaPublicId);

    // 🏭 Filtrar por status (kanban / dashboard)
    List<OrdemProducao> findAllByEmpresaPublicIdAndStatus(
            UUID empresaPublicId,
            StatusOrdemProducao status
    );

    // 🔥 Ordens ativas (produção + aguardando)
    List<OrdemProducao> findAllByEmpresaPublicIdAndStatusIn(
            UUID empresaPublicId,
            List<StatusOrdemProducao> status
    );
}