package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.estoque.EstoqueProdutoRequest;
import app.forgeon.forgeon_api.dto.estoque.EstoqueProdutoResponse;
import app.forgeon.forgeon_api.model.EstoqueProduto;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.EstoqueProdutoRepository;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueProdutoService {

    private final EstoqueProdutoRepository estoqueRepo;
    private final ProdutoRepository produtoRepo;

    public EstoqueProdutoService(EstoqueProdutoRepository estoqueRepo, ProdutoRepository produtoRepo) {
        this.estoqueRepo = estoqueRepo;
        this.produtoRepo = produtoRepo;
    }

    public List<EstoqueProdutoResponse> listarPorEmpresa(Long empresaId) {
        return estoqueRepo.findByEmpresaId(empresaId).stream()
                .map(e -> new EstoqueProdutoResponse(
                        e.getId(),
                        e.getProduto().getNome(),
                        e.getProduto().getSku(),
                        e.getQuantidade()
                ))
                .collect(Collectors.toList());
    }

    public EstoqueProdutoResponse criarOuAtualizar(EstoqueProdutoRequest dto) {
        Produto produto = produtoRepo.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        EstoqueProduto estoque = estoqueRepo.findByEmpresaIdAndProdutoId(dto.getEmpresaId(), dto.getProdutoId())
                .orElse(new EstoqueProduto());

        estoque.setEmpresaId(dto.getEmpresaId());
        estoque.setProduto(produto);
        estoque.setQuantidade(dto.getQuantidade());

        estoqueRepo.save(estoque);

        return new EstoqueProdutoResponse(
                estoque.getId(),
                produto.getNome(),
                produto.getSku(),
                estoque.getQuantidade()
        );
    }

    public void ajustarQuantidade(Long empresaId, Long produtoId, Integer delta) {
        EstoqueProduto estoque = estoqueRepo.findByEmpresaIdAndProdutoId(empresaId, produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no estoque"));

        estoque.setQuantidade(estoque.getQuantidade() + delta);
        estoqueRepo.save(estoque);
    }

    public void deletar(Long id) {
        estoqueRepo.deleteById(id);
    }
}
