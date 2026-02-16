package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.enums.StatusOrdemProducao;
import app.forgeon.forgeon_api.enums.StatusPedido;
import app.forgeon.forgeon_api.model.OrdemProducao;
import app.forgeon.forgeon_api.model.Pedido;
import app.forgeon.forgeon_api.repository.OrdemProducaoRepository;
import app.forgeon.forgeon_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdemProducaoService {

    private final OrdemProducaoRepository ordemRepository;
    private final PedidoRepository pedidoRepository;

    // 🔥 Criar ordem ao confirmar pagamento
    @Transactional
    public void criarOrdem(Pedido pedido) {

        if (ordemRepository.existsByPedidoPublicId(pedido.getPublicId())) {
            return;
        }

        OrdemProducao ordem = new OrdemProducao();
        ordem.setPedido(pedido);
        ordem.setEmpresaPublicId(pedido.getEmpresaPublicId());
        ordem.setStatus(StatusOrdemProducao.AGUARDANDO);

        ordemRepository.save(ordem);
    }

    // ▶️ Iniciar produção
    @Transactional
    public void iniciar(UUID ordemId, UUID empresaPublicId) {

        OrdemProducao ordem = buscarOrdem(ordemId, empresaPublicId);

        if (ordem.getStatus() != StatusOrdemProducao.AGUARDANDO) {
            throw new RuntimeException("Ordem não pode ser iniciada");
        }

        ordem.setStatus(StatusOrdemProducao.EM_PRODUCAO);
        ordem.setIniciadoEm(LocalDateTime.now());
    }

    // ✅ Finalizar produção
    @Transactional
    public void finalizar(UUID ordemId, UUID empresaPublicId) {

        OrdemProducao ordem = buscarOrdem(ordemId, empresaPublicId);

        if (ordem.getStatus() != StatusOrdemProducao.EM_PRODUCAO) {
            throw new RuntimeException("Ordem não está em produção");
        }

        ordem.setStatus(StatusOrdemProducao.FINALIZADA);
        ordem.setFinalizadoEm(LocalDateTime.now());

        Pedido pedido = ordem.getPedido();
        pedido.setStatus(StatusPedido.PRONTO_PARA_FATURAR);
    }

    // ❌ Cancelar produção
    @Transactional
    public void cancelar(UUID ordemId, UUID empresaPublicId) {

        OrdemProducao ordem = buscarOrdem(ordemId, empresaPublicId);

        ordem.setStatus(StatusOrdemProducao.CANCELADA);

        Pedido pedido = ordem.getPedido();
        pedido.setStatus(StatusPedido.CANCELADO);
    }

    // 📋 Listar fila
    @Transactional(readOnly = true)
    public List<OrdemProducao> listarFila(UUID empresaPublicId) {
        return ordemRepository.findAllByEmpresaPublicId(empresaPublicId);
    }

    // 🔍 Helper
    private OrdemProducao buscarOrdem(UUID ordemId, UUID empresaPublicId) {
        OrdemProducao ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new RuntimeException("Ordem não encontrada"));

        if (!ordem.getEmpresaPublicId().equals(empresaPublicId)) {
            throw new RuntimeException("Ordem não pertence à empresa");
        }

        return ordem;
    }
}