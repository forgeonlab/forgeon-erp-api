package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.produto.ProdutoRequest;
import app.forgeon.forgeon_api.dto.produto.ProdutoResponse;
import app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import app.forgeon.forgeon_api.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /* =========================
       LISTAR PRODUTOS
    ========================= */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarPorEmpresa(UUID empresaPublicId) {

        return produtoRepository
                .findByEmpresaPublicIdAndAtivoTrue(empresaPublicId)
                .stream()
                .map(ProdutoResponse::fromEntity)
                .toList();
    }

    /* =========================
       CRIAR PRODUTO
    ========================= */
    @Transactional
    public ProdutoResponse criar(
            ProdutoRequest dto,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ) {

        // 🔒 valida SKU duplicado
        if (produtoRepository.existsByEmpresaPublicIdAndSku(
                empresaPublicId,
                dto.getSku()
        )) {
            throw new RuntimeException("SKU já cadastrado para essa empresa");
        }

        Produto produto = new Produto();

        produto.setPublicId(UUID.randomUUID());
        produto.setEmpresaPublicId(empresaPublicId);

        produto.setSku(dto.getSku());
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());

        produto.setAtivo(
                dto.getAtivo() != null ? dto.getAtivo() : true
        );

        produto.setCriadoEm(LocalDateTime.now());
        produto.setCriadoPor(usuarioPublicId);

        Produto salvo = produtoRepository.save(produto);

        return ProdutoResponse.fromEntity(salvo);
    }

    /* =========================
       ATUALIZAR PRODUTO
    ========================= */
    @Transactional
    public ProdutoResponse atualizar(
            UUID produtoPublicId,
            ProdutoRequest dto,
            UUID empresaPublicId
    ) {

        Produto produto = produtoRepository
                .findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
                        produtoPublicId,
                        empresaPublicId
                )
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado")
                );

        // 🔒 valida SKU duplicado (ignorando o próprio produto)
        if (produtoRepository.existsByEmpresaPublicIdAndSkuAndPublicIdNot(
                empresaPublicId,
                dto.getSku(),
                produtoPublicId
        )) {
            throw new RuntimeException("SKU já cadastrado em outro produto");
        }

        produto.setSku(dto.getSku());
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());

        if (dto.getAtivo() != null) {
            produto.setAtivo(dto.getAtivo());
        }

        produto.setAtualizadoEm(LocalDateTime.now());

        Produto atualizado = produtoRepository.save(produto);

        return ProdutoResponse.fromEntity(atualizado);
    }

    /* =========================
       DELETAR PRODUTO (SOFT DELETE)
    ========================= */
    @Transactional
    public void deletar(
            UUID produtoPublicId,
            UUID empresaPublicId
    ) {

        Produto produto = produtoRepository
                .findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
                        produtoPublicId,
                        empresaPublicId
                )
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado")
                );

        produto.setAtivo(false);
        produto.setAtualizadoEm(LocalDateTime.now());

        produtoRepository.save(produto);
    }

    /* =========================
       VENDAS POR PRODUTO
    ========================= */
    @Transactional(readOnly = true)
    public List<ProdutoVendaDTO> listarVendasPorProduto(UUID empresaPublicId) {
        return vendaRepository.vendasPorProduto(empresaPublicId);
    }
}
