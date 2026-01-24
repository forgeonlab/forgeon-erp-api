package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.producao.ProducaoRequest;
import app.forgeon.forgeon_api.dto.producao.ProducaoResponse;
import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.model.Impressora;
import app.forgeon.forgeon_api.model.Producao;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.repository.ImpressoraRepository;
import app.forgeon.forgeon_api.repository.ProducaoRepository;
import app.forgeon.forgeon_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProducaoService {

    private final ProducaoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ImpressoraRepository impressoraRepository;

    public ProducaoService(ProducaoRepository repository,
                           ProdutoRepository produtoRepository,
                           ImpressoraRepository impressoraRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.impressoraRepository = impressoraRepository;
    }

    public List<ProducaoResponse> listarPorEmpresa(UUID empresaId) {
        return repository.findByEmpresaIdOrderByDataDesc(empresaId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProducaoResponse criar(ProducaoRequest dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        Impressora impressora = dto.getImpressoraId() != null
                ? impressoraRepository.findById(dto.getImpressoraId())
                .orElseThrow(() -> new RuntimeException("Impressora não encontrada"))
                : null;

        Producao p = new Producao();
        p.setEmpresaId(dto.getEmpresaId());
        p.setProduto(produto);
        p.setImpressora(impressora);
        p.setQuantidadePlanejada(dto.getQuantidadePlanejada());
        p.setQuantidadeBoa(dto.getQuantidadeBoa() != null ? dto.getQuantidadeBoa() : 0);
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusProducao.PLANEJADA);
        p.setInicio(dto.getInicio());
        p.setFimPrevisto(dto.getFimPrevisto());

        repository.save(p);
        return toResponse(p);
    }

    public ProducaoResponse atualizarStatus(UUID id, StatusProducao novoStatus) {
        Producao p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        p.setStatus(novoStatus);
        repository.save(p);
        return toResponse(p);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    private ProducaoResponse toResponse(Producao p) {
        return new ProducaoResponse(
                p.getId(),
                p.getProduto().getNome(),
                p.getImpressora() != null ? p.getImpressora().getNome() : "-",
                p.getQuantidadePlanejada(),
                p.getQuantidadeBoa(),
                p.getStatus(),
                p.getInicio(),
                p.getFimPrevisto(),
                p.getData()
        );
    }
}
