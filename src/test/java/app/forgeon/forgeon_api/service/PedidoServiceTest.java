package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.pedido.CriarPedidoRequestDTO;
import app.forgeon.forgeon_api.dto.pedido.ItemPedidoRequestDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoResponseDTO;
import app.forgeon.forgeon_api.enums.TipoProduto;
import app.forgeon.forgeon_api.model.Pedido;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.CupomRepository;
import app.forgeon.forgeon_api.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoServiceTest {

    @Test
    void criarPedido_reservaEstoqueQuandoTipoEstoque() {
        ProdutoService produtoService = Mockito.mock(ProdutoService.class);
        EstoqueService estoqueService = Mockito.mock(EstoqueService.class);
        PedidoRepository pedidoRepository = Mockito.mock(PedidoRepository.class);
        CupomRepository cupomRepository = Mockito.mock(CupomRepository.class);
        OrdemProducaoService ordemProducaoService = Mockito.mock(OrdemProducaoService.class);

        PedidoService service = new PedidoService(
                produtoService,
                estoqueService,
                pedidoRepository,
                cupomRepository,
                ordemProducaoService
        );

        UUID empresaId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        UUID produtoPublicId = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setTipo(TipoProduto.ESTOQUE);
        produto.setPrecoVenda(new BigDecimal("10.00"));

        when(produtoService.buscarEntidadePorPublicId(produtoPublicId, empresaId))
                .thenReturn(produto);
        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> {
                    Pedido pedido = invocation.getArgument(0);
                    if (pedido.getPublicId() == null) {
                        pedido.setPublicId(UUID.randomUUID());
                    }
                    return pedido;
                });

        CriarPedidoRequestDTO dto = new CriarPedidoRequestDTO(
                List.of(new ItemPedidoRequestDTO(produtoPublicId, 2)),
                null
        );

        PedidoResponseDTO response = service.criarPedido(dto, empresaId, usuarioId);

        assertEquals(0, response.valorTotal().compareTo(new BigDecimal("20.00")));
        verify(estoqueService).reservarSePossivel(eq(produto), eq(2), anyString(), eq(usuarioId));
    }

    @Test
    void criarPedido_naoReservaQuandoTipoNaoEstoque() {
        ProdutoService produtoService = Mockito.mock(ProdutoService.class);
        EstoqueService estoqueService = Mockito.mock(EstoqueService.class);
        PedidoRepository pedidoRepository = Mockito.mock(PedidoRepository.class);
        CupomRepository cupomRepository = Mockito.mock(CupomRepository.class);
        OrdemProducaoService ordemProducaoService = Mockito.mock(OrdemProducaoService.class);

        PedidoService service = new PedidoService(
                produtoService,
                estoqueService,
                pedidoRepository,
                cupomRepository,
                ordemProducaoService
        );

        UUID empresaId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        UUID produtoPublicId = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setTipo(TipoProduto.SERVICO);
        produto.setPrecoVenda(new BigDecimal("10.00"));

        when(produtoService.buscarEntidadePorPublicId(produtoPublicId, empresaId))
                .thenReturn(produto);
        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CriarPedidoRequestDTO dto = new CriarPedidoRequestDTO(
                List.of(new ItemPedidoRequestDTO(produtoPublicId, 2)),
                null
        );

        PedidoResponseDTO response = service.criarPedido(dto, empresaId, usuarioId);

        assertEquals(0, response.valorTotal().compareTo(new BigDecimal("20.00")));
        verify(estoqueService, never()).reservarSePossivel(any(), anyInt(), anyString(), any());
    }
}
