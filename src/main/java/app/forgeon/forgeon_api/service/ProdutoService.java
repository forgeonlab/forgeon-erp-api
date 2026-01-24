package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.produto.ProdutoRequest;
import app.forgeon.forgeon_api.dto.produto.ProdutoResponse;
import app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import app.forgeon.forgeon_api.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            VendaRepository vendaRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.vendaRepository = vendaRepository;
    }

    public List<ProdutoResponse> listarPorEmpresa(UUID empresaId) {
        return produtoRepository.findByEmpresaId(empresaId)
                .stream()
                .map(p -> new ProdutoResponse(
                        p.getId(),
                        p.getSku(),
                        p.getNome(),
                        p.getPrecoVenda(),
                        p.getAtivo()
                ))
                .toList();
    }

    public ProdutoResponse criar(ProdutoRequest dto) {
        Produto produto = new Produto();
        produto.setEmpresaId(dto.getEmpresaId());
        produto.setSku(dto.getSku());
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        produtoRepository.save(produto);

        return new ProdutoResponse(
                produto.getId(),
                produto.getSku(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getAtivo()
        );
    }

    public ProdutoResponse atualizar(UUID id, ProdutoRequest dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setAtivo(dto.getAtivo());

        produtoRepository.save(produto);

        return new ProdutoResponse(
                produto.getId(),
                produto.getSku(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getAtivo()
        );
    }

    public void deletar(UUID id) {
        produtoRepository.deleteById(id);
    }

    // 📊 MÉTRICA / GRÁFICO
    public List<ProdutoVendaDTO> vendasPorProduto(UUID empresaId) {
        return vendaRepository.vendasPorProduto(empresaId);
    }
}
