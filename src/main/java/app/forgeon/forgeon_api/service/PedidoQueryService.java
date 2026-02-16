package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.pedido.ItemPedidoResponseDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoDetalhadoResponseDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoListResponseDTO;
import app.forgeon.forgeon_api.model.Pedido;
import app.forgeon.forgeon_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoQueryService {

    private final PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public Page<PedidoListResponseDTO> listar(
            UUID empresaPublicId,
            Pageable pageable
    ) {
        return pedidoRepository
                .findAllByEmpresaPublicId(empresaPublicId, pageable)
                .map(p -> new PedidoListResponseDTO(
                        p.getPublicId(),
                        p.getStatus(),
                        p.getValorTotal(),
                        p.getCriadoEm()
                ));
    }

    @Transactional(readOnly = true)
    public PedidoDetalhadoResponseDTO buscarDetalhe(
            UUID publicId,
            UUID empresaPublicId
    ) {
        Pedido pedido = pedidoRepository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow();

        return new PedidoDetalhadoResponseDTO(
                pedido.getPublicId(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getValorDesconto(),
                pedido.getCupom() != null ? pedido.getCupom().getCodigo() : null,
                pedido.getCriadoEm(),
                pedido.getItens().stream()
                        .map(i -> new ItemPedidoResponseDTO(
                                i.getProduto().getPublicId(),
                                i.getProduto().getSku(),
                                i.getProduto().getNome(),
                                i.getQuantidade(),
                                i.getPrecoUnitario(),
                                i.getSubtotal()
                        ))
                        .toList()
        );
    }
}
