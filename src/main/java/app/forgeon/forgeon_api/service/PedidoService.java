package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.pedido.CriarPedidoRequestDTO;
import app.forgeon.forgeon_api.dto.pedido.ItemPedidoRequestDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoResponseDTO;
import app.forgeon.forgeon_api.enums.StatusPedido;
import app.forgeon.forgeon_api.enums.TipoProduto;
import app.forgeon.forgeon_api.exception.PedidoStatusInvalidoException;
import app.forgeon.forgeon_api.model.Cupom;
import app.forgeon.forgeon_api.model.ItemPedido;
import app.forgeon.forgeon_api.model.Pedido;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.CupomRepository;
import app.forgeon.forgeon_api.repository.OrdemProducaoRepository;
import app.forgeon.forgeon_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;
    private final PedidoRepository pedidoRepository;
    private final CupomRepository cupomRepository;
    private final OrdemProducaoService ordemProducaoService;

    @Transactional
    public PedidoResponseDTO criarPedido(
            CriarPedidoRequestDTO dto,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ) {
        Pedido pedido = new Pedido();
        pedido.setEmpresaPublicId(empresaPublicId);
        pedido.setCriadoPor(usuarioPublicId);

        BigDecimal total = BigDecimal.ZERO;

        // 1️⃣ itens + reserva de estoque
        for (ItemPedidoRequestDTO itemDTO : dto.itens()) {

            Produto produto = produtoService.buscarEntidadePorPublicId(
                    itemDTO.produtoPublicId(),
                    empresaPublicId
            );

            if (controlaEstoque(produto)) {
                // Reserva somente quando houver estoque disponivel.
                estoqueService.reservarSePossivel(
                        produto,
                        itemDTO.quantidade(),
                        "Pedido em criacao",
                        usuarioPublicId
                );
            }

            BigDecimal precoUnit = produto.getPrecoVenda(); // 🔒 preço congelado
            BigDecimal subtotal = precoUnit.multiply(
                    BigDecimal.valueOf(itemDTO.quantidade())
            );

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(precoUnit);
            item.setSubtotal(subtotal);

            pedido.getItens().add(item);
            total = total.add(subtotal);
        }

        // 2️⃣ cupom / desconto
        BigDecimal desconto = BigDecimal.ZERO;
        Cupom cupom = null;

        if (dto.cupom() != null && !dto.cupom().isBlank()) {

            cupom = cupomRepository
                    .findByCodigoAndAtivoTrue(dto.cupom())
                    .orElseThrow(() -> new RuntimeException("Cupom inválido"));

            if (cupom.getExpiraEm() != null &&
                    cupom.getExpiraEm().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Cupom expirado");
            }

            desconto = total
                    .multiply(cupom.getPercentual())
                    .divide(BigDecimal.valueOf(100));

            pedido.setCupom(cupom);
            pedido.setValorDesconto(desconto);
        } else {
            pedido.setValorDesconto(BigDecimal.ZERO);
        }

        // 3️⃣ total final congelado
        pedido.setValorTotal(total.subtract(desconto));

        pedidoRepository.save(pedido);

        return new PedidoResponseDTO(
                pedido.getPublicId(),
                pedido.getStatus(), // CRIADO
                pedido.getValorTotal()
        );
    }


    @Transactional
    public void faturar(UUID pedidoPublicId, UUID empresaPublicId, UUID usuarioPublicId) {

        Pedido pedido = pedidoRepository.findByPublicIdAndEmpresaPublicId(
                pedidoPublicId, empresaPublicId
        ).orElseThrow();

        if (pedido.getStatus() != StatusPedido.CONFIRMADO && pedido.getStatus() != StatusPedido.PRONTO_PARA_FATURAR) {
            throw new PedidoStatusInvalidoException("faturado", pedido.getStatus());
        }

        for (ItemPedido item : pedido.getItens()) {
            if (controlaEstoque(item.getProduto())) {
                estoqueService.saida(
                        item.getProduto(),
                        item.getQuantidade(),
                        "Faturamento pedido " + pedidoPublicId,
                        usuarioPublicId
                );
            }
        }

        pedido.setStatus(StatusPedido.FATURADO);
    }

    @Transactional
    public void cancelar(UUID pedidoPublicId, UUID empresaPublicId, UUID usuarioPublicId) {

        Pedido pedido = pedidoRepository.findByPublicIdAndEmpresaPublicId(
                pedidoPublicId, empresaPublicId
        ).orElseThrow();

        if (pedido.getStatus() != StatusPedido.CRIADO
                && pedido.getStatus() != StatusPedido.CONFIRMADO) {
            throw new PedidoStatusInvalidoException("cancelado", pedido.getStatus());
        }

        for (ItemPedido item : pedido.getItens()) {
            if (controlaEstoque(item.getProduto())) {
                estoqueService.cancelarReservaSePossivel(
                        item.getProduto(),
                        item.getQuantidade(),
                        "Cancelamento pedido " + pedidoPublicId,
                        usuarioPublicId
                );
            }
        }

        pedido.setStatus(StatusPedido.CANCELADO);
    }

    @Transactional
    public void confirmarPagamento(UUID pedidoPublicId, UUID empresaPublicId) {

        Pedido pedido = pedidoRepository
                .findByPublicIdAndEmpresaPublicId(pedidoPublicId, empresaPublicId)
                .orElseThrow();

        if (pedido.getStatus() != StatusPedido.CRIADO) {
            throw new PedidoStatusInvalidoException("confirmado", pedido.getStatus());
        }

        boolean precisaProducao = pedido.getItens().stream()
                .anyMatch(item -> item.getProduto().getTipo() == TipoProduto.SOB_ENCOMENDA);

        if (precisaProducao) {
            ordemProducaoService.criarOrdem(pedido);
            pedido.setStatus(StatusPedido.EM_PRODUCAO);
        } else {
            pedido.setStatus(StatusPedido.PRONTO_PARA_FATURAR);
        }
    }

    @Transactional
    public void marcarEmRota(UUID pedidoPublicId, UUID empresaPublicId) {
        Pedido pedido = pedidoRepository.findByPublicIdAndEmpresaPublicId(
                pedidoPublicId, empresaPublicId
        ).orElseThrow();

        if (pedido.getStatus() != StatusPedido.FATURADO) {
            throw new PedidoStatusInvalidoException("enviado", pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.EM_ROTA);
    }

    @Transactional
    public void finalizar(UUID pedidoPublicId, UUID empresaPublicId) {
        Pedido pedido = pedidoRepository.findByPublicIdAndEmpresaPublicId(
                pedidoPublicId, empresaPublicId
        ).orElseThrow();

        if (pedido.getStatus() != StatusPedido.EM_ROTA
                && pedido.getStatus() != StatusPedido.FATURADO) {
            throw new PedidoStatusInvalidoException("finalizado", pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
    }

    private BigDecimal aplicarDesconto(
            BigDecimal total,
            Cupom cupom
    ) {
        BigDecimal desconto = total
                .multiply(cupom.getPercentual())
                .divide(BigDecimal.valueOf(100));

        return desconto;
    }

    private boolean controlaEstoque(Produto produto) {
        return produto.getTipo() == TipoProduto.ESTOQUE;
    }

}
