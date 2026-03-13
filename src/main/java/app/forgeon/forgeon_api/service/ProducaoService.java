package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.producao.*;
import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.model.Impressora;
import app.forgeon.forgeon_api.model.Producao;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.ImpressoraRepository;
import app.forgeon.forgeon_api.repository.ProducaoRepository;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProducaoService {

    private final ProducaoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ImpressoraRepository impressoraRepository;
    private final EstoqueService estoqueService;

    /* ================= LISTAR ================= */

    public List<ProducaoResponse> listarPorEmpresa(UUID empresaPublicId) {

        return repository
                .findByEmpresaPublicIdOrderByCriadoEmDesc(empresaPublicId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ================= CRIAR ================= */

    public ProducaoResponse criar(
            ProducaoRequest dto,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ) {

        Produto produto = produtoRepository
                .findByPublicIdAndEmpresaPublicId(
                        dto.produtoPublicId(),
                        empresaPublicId
                )
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Impressora impressora = null;

        if (dto.impressoraPublicId() != null) {
            impressora = impressoraRepository
                    .findByPublicIdAndEmpresaPublicId(
                            dto.impressoraPublicId(),
                            empresaPublicId
                    )
                    .orElseThrow(() -> new RuntimeException("Impressora não encontrada"));
        }

        /* ===== Bloqueio de impressora ===== */

        if (impressora != null) {
            boolean ocupada = repository
                    .existsByEmpresaPublicIdAndImpressora_PublicIdAndStatus(
                            empresaPublicId,
                            impressora.getId(),
                            StatusProducao.EM_PRODUCAO
                    );

            if (ocupada) {
                throw new RuntimeException("Impressora já está em produção");
            }
        }

        /* ===== Criar produção ===== */

        Producao p = new Producao();
        p.setEmpresaPublicId(empresaPublicId);
        p.setProduto(produto);
        p.setImpressora(impressora);
        p.setQuantidadePlanejada(dto.quantidadePlanejada());
        p.setFimPrevisto(dto.fimPrevisto());
        p.setQuantidadeBoa(0);
        p.setStatus(StatusProducao.PLANEJADA);
        p.setCriadoPor(usuarioPublicId);

        repository.save(p);

        /* ===== Reserva opcional ===== */

        if (dto.reservarEstoque()) {
            estoqueService.reservar(
                    produto,
                    dto.quantidadePlanejada(),
                    "Reserva Produção " + p.getPublicId(),
                    usuarioPublicId
            );
        }

        return toResponse(p);
    }

    /* ================= ATUALIZAR STATUS ================= */

    public ProducaoResponse atualizarStatus(
            UUID publicId,
            StatusProducao novoStatus,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ) {

        Producao p = repository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        p.setStatus(novoStatus);

        if (novoStatus == StatusProducao.EM_PRODUCAO) {
            p.setInicio(LocalDateTime.now());
        }

        if (novoStatus == StatusProducao.FINALIZADA) {

            p.setFimReal(LocalDateTime.now());

            if (p.getQuantidadeBoa() > 0) {
                estoqueService.entrada(
                        p.getProduto(),
                        p.getQuantidadeBoa(),
                        "Produção " + p.getPublicId(),
                        usuarioPublicId
                );
            }
        }

        repository.save(p);

        return toResponse(p);
    }

    /* ================= ATUALIZAR QUANTIDADE BOA ================= */

    public ProducaoResponse atualizarQuantidadeBoa(
            UUID publicId,
            AtualizarQuantidadeBoaRequest dto,
            UUID empresaPublicId
    ) {

        Producao p = repository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        if (dto.quantidadeBoa() > p.getQuantidadePlanejada()) {
            throw new RuntimeException("Quantidade boa não pode ser maior que planejada");
        }

        p.setQuantidadeBoa(dto.quantidadeBoa());

        repository.save(p);

        return toResponse(p);
    }

    /* ================= DELETAR ================= */

    public void deletar(UUID publicId, UUID empresaPublicId) {

        Producao p = repository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        repository.delete(p);
    }

    /* ================= DASHBOARD ================= */

    public ProducaoDashboardDTO dashboard(UUID empresaPublicId) {

        return new ProducaoDashboardDTO(
                repository.countByEmpresaPublicId(empresaPublicId),
                repository.countByEmpresaPublicIdAndStatus(empresaPublicId, StatusProducao.PLANEJADA),
                repository.countByEmpresaPublicIdAndStatus(empresaPublicId, StatusProducao.EM_PRODUCAO),
                repository.countByEmpresaPublicIdAndStatus(empresaPublicId, StatusProducao.FINALIZADA),
                repository.countByEmpresaPublicIdAndStatus(empresaPublicId, StatusProducao.PAUSADA)
        );
    }

    /* ================= MAPPER ================= */

    private ProducaoResponse toResponse(Producao p) {

        return new ProducaoResponse(
                p.getPublicId(),

                p.getProduto().getNome(),
                p.getProduto().getPublicId(),

                p.getImpressora() != null ? p.getImpressora().getNome() : "-",
                p.getImpressora() != null ? p.getImpressora().getId() : null,

                p.getQuantidadePlanejada(),
                p.getQuantidadeBoa(),

                p.getStatus(),

                p.getInicio(),
                p.getFimPrevisto(),
                p.getFimReal(),

                p.getCriadoEm(),
                p.getAtualizadoEm()
        );
    }
}