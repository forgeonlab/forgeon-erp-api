package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoEstoqueProdutoRequest;
import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoEstoqueProdutoResponse;
import app.forgeon.forgeon_api.model.MovimentacaoEstoqueProduto;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.MovimentacaoEstoqueProdutoRepository;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoqueProdutoService {

    private final MovimentacaoEstoqueProdutoRepository repo;
    private final ProdutoRepository produtoRepo;

    public MovimentacaoEstoqueProdutoService(MovimentacaoEstoqueProdutoRepository repo, ProdutoRepository produtoRepo) {
        this.repo = repo;
        this.produtoRepo = produtoRepo;
    }

    public List<MovimentacaoEstoqueProdutoResponse> listarPorEmpresa(UUID empresaId) {
        return repo.findByEmpresaIdOrderByDataDesc(empresaId)
                .stream()
                .map(m -> new MovimentacaoEstoqueProdutoResponse(
                        m.getId(),
                        m.getProduto().getNome(),
                        m.getTipo(),
                        m.getOrigem(),
                        m.getQuantidade(),
                        m.getData()
                ))
                .collect(Collectors.toList());
    }

    public MovimentacaoEstoqueProdutoResponse registrar(MovimentacaoEstoqueProdutoRequest dto) {
        Produto produto = produtoRepo.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        MovimentacaoEstoqueProduto mov = new MovimentacaoEstoqueProduto();
        mov.setEmpresaId(dto.getEmpresaId());
        mov.setProduto(produto);
        mov.setTipo(dto.getTipo());
        mov.setQuantidade(dto.getQuantidade());
        mov.setOrigem(dto.getOrigem());
        mov.setReferenciaId(dto.getReferenciaId());

        repo.save(mov);

        return new MovimentacaoEstoqueProdutoResponse(
                mov.getId(),
                produto.getNome(),
                mov.getTipo(),
                mov.getOrigem(),
                mov.getQuantidade(),
                mov.getData()
        );
    }

    public void deletar(UUID id) {
        repo.deleteById(id);
    }
}
