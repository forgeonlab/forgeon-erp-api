package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.estoque.EstoqueDashboardDTO;
import app.forgeon.forgeon_api.dto.estoque.EstoqueListaDTO;
import app.forgeon.forgeon_api.dto.estoque.EstoqueResponseDTO;
import app.forgeon.forgeon_api.dto.estoque.MovimentacaoEstoqueResponseDTO;
import app.forgeon.forgeon_api.enums.TipoMovimentacaoEstoque;
import app.forgeon.forgeon_api.model.Estoque;
import app.forgeon.forgeon_api.model.MovimentacaoEstoque;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.EstoqueRepository;
import app.forgeon.forgeon_api.repository.MovimentacaoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    @Transactional(readOnly = true)
    public List<EstoqueListaDTO> listar(UUID empresaPublicId) {
        return estoqueRepository.listar(empresaPublicId);
    }

    @Transactional
    public void entrada(
            Produto produto,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseGet(() -> criarEstoque(produto));

        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.ENTRADA,
                quantidade,
                referencia,
                usuarioPublicId
        );
    }

    @Transactional
    public void saida(
            Produto produto,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        if (estoque.getQuantidade() - estoque.getReservado() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.SAIDA,
                quantidade,
                referencia,
                usuarioPublicId
        );
    }

    private Estoque criarEstoque(Produto produto) {
        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidade(0);
        estoque.setReservado(0);
        return estoqueRepository.save(estoque);
    }

    private void registrarMovimentacao(
            Produto produto,
            TipoMovimentacaoEstoque tipo,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setProduto(produto);
        mov.setTipo(tipo);
        mov.setQuantidade(quantidade);
        mov.setReferencia(referencia);
        mov.setCriadoPor(usuarioPublicId);
        movimentacaoRepository.save(mov);
    }

    @Transactional
    public void ajuste(
            Produto produto,
            Integer quantidadeFinal,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseGet(() -> criarEstoque(produto));

        int diferenca = quantidadeFinal - estoque.getQuantidade();

        estoque.setQuantidade(quantidadeFinal);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.AJUSTE,
                diferenca,
                referencia,
                usuarioPublicId
        );
    }

    @Transactional
    public EstoqueResponseDTO consultarEstoque(Produto produto) {

        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseGet(() -> criarEstoque(produto));

        return new EstoqueResponseDTO(
                produto.getPublicId(),
                produto.getSku(),
                produto.getNome(),
                estoque.getQuantidade(),
                estoque.getReservado(),
                estoque.getQuantidade() - estoque.getReservado()
        );
    }

    @Transactional
    public List<MovimentacaoEstoqueResponseDTO> listarMovimentacoes(Produto produto) {

        return movimentacaoRepository
                .findAllByProdutoOrderByCriadoEmDesc(produto)
                .stream()
                .map(m -> new MovimentacaoEstoqueResponseDTO(
                        m.getTipo(),
                        m.getQuantidade(),
                        m.getReferencia(),
                        m.getCriadoEm()
                ))
                .toList();
    }

    @Transactional
    public void reservar(
            Produto produto,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        if (estoque.getQuantidade() - estoque.getReservado() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para reserva");
        }

        estoque.setReservado(estoque.getReservado() + quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.RESERVA,
                quantidade,
                referencia,
                usuarioPublicId
        );
    }

    @Transactional(readOnly = true)
    public void validarDisponibilidade(
            Produto produto,
            Integer quantidade
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto).orElse(null);
        if (estoque == null) {
            // Sem registro de estoque: nao bloquear confirmacao do pedido.
            return;
        }

        if (estoque.getQuantidade() - estoque.getReservado() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }
    }

    @Transactional
    public boolean reservarSePossivel(
            Produto produto,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto).orElse(null);
        if (estoque == null) {
            return false;
        }

        if (estoque.getQuantidade() - estoque.getReservado() < quantidade) {
            return false;
        }

        estoque.setReservado(estoque.getReservado() + quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.RESERVA,
                quantidade,
                referencia,
                usuarioPublicId
        );

        return true;
    }

    @Transactional
    public void cancelarReserva(
            Produto produto,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        estoque.setReservado(estoque.getReservado() - quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.CANCELAMENTO_RESERVA,
                quantidade,
                referencia,
                usuarioPublicId
        );
    }

    @Transactional
    public boolean cancelarReservaSePossivel(
            Produto produto,
            Integer quantidade,
            String referencia,
            UUID usuarioPublicId
    ) {
        Estoque estoque = estoqueRepository.findByProduto(produto).orElse(null);
        if (estoque == null) {
            return false;
        }

        estoque.setReservado(estoque.getReservado() - quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(
                produto,
                TipoMovimentacaoEstoque.CANCELAMENTO_RESERVA,
                quantidade,
                referencia,
                usuarioPublicId
        );

        return true;
    }

    @Transactional
    public EstoqueDashboardDTO dashboard() {
        return new EstoqueDashboardDTO(
                estoqueRepository.count(),
                estoqueRepository.countSemEstoque(),
                estoqueRepository.countComReserva()
        );
    }

}
