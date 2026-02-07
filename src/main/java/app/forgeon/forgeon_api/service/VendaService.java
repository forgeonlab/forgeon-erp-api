package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.venda.VendaRequest;
import app.forgeon.forgeon_api.dto.venda.VendaResponse;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.model.Venda;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import app.forgeon.forgeon_api.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ProdutoRepository produtoRepository
    ) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    /* =========================
       LISTAR VENDAS
    ========================= */
    @Transactional(readOnly = true)
    public List<VendaResponse> listarPorEmpresa(UUID empresaPublicId) {

        return vendaRepository
                .findByEmpresaPublicIdAndAtivoTrue(empresaPublicId)
                .stream()
                .map(VendaResponse::fromEntity)
                .toList();
    }

    /* =========================
       CRIAR VENDA
    ========================= */
    @Transactional
    public VendaResponse criar(
            VendaRequest dto,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ) {

        // 🔒 busca produto da mesma empresa
        Produto produto = produtoRepository
                .findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
                        dto.getProdutoPublicId(),
                        empresaPublicId
                )
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado")
                );

        Venda venda = new Venda();
        venda.setPublicId(UUID.randomUUID());
        venda.setEmpresaPublicId(empresaPublicId);

        venda.setProdutoPublicId(produto.getPublicId());
        venda.setProdutoNome(produto.getNome());
        venda.setQuantidade(dto.getQuantidade());

        BigDecimal quantidade = BigDecimal.valueOf(dto.getQuantidade());
        BigDecimal precoUnitario = produto.getPrecoVenda();
        BigDecimal total = precoUnitario.multiply(quantidade);

        venda.setPrecoUnitario(precoUnitario);
        venda.setTotal(total);

        venda.setStatus(StatusVenda.CONCLUIDA);
        venda.setAtivo(true);

        venda.setCriadoEm(LocalDateTime.now());
        venda.setCriadoPor(usuarioPublicId);

        Venda salva = vendaRepository.save(venda);

        return VendaResponse.fromEntity(salva);
    }

    /* =========================
       ATUALIZAR STATUS
    ========================= */
    @Transactional
    public VendaResponse atualizarStatus(
            UUID vendaPublicId,
            StatusVenda status,
            UUID empresaPublicId
    ) {

        Venda venda = vendaRepository
                .findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
                        vendaPublicId,
                        empresaPublicId
                )
                .orElseThrow(() ->
                        new RuntimeException("Venda não encontrada")
                );

        venda.setStatus(status);
        venda.setAtualizadoEm(LocalDateTime.now());

        Venda atualizada = vendaRepository.save(venda);

        return VendaResponse.fromEntity(atualizada);
    }

    /* =========================
       DELETAR VENDA (SOFT DELETE)
    ========================= */
    @Transactional
    public void deletar(
            UUID vendaPublicId,
            UUID empresaPublicId
    ) {

        Venda venda = vendaRepository
                .findByPublicIdAndEmpresaPublicIdAndAtivoTrue(
                        vendaPublicId,
                        empresaPublicId
                )
                .orElseThrow(() ->
                        new RuntimeException("Venda não encontrada")
                );

        // regra de negócio opcional
        if (venda.getStatus() == StatusVenda.CONCLUIDA) {
            throw new RuntimeException("Não é possível excluir venda concluída");
        }

        venda.setAtivo(false);
        venda.setAtualizadoEm(LocalDateTime.now());

        vendaRepository.save(venda);
    }
}
