package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.historico.HistoricoProducaoRequest;
import app.forgeon.forgeon_api.dto.historico.HistoricoProducaoResponse;
import app.forgeon.forgeon_api.model.HistoricoProducao;
import app.forgeon.forgeon_api.model.Producao;
import app.forgeon.forgeon_api.repository.HistoricoProducaoRepository;
import app.forgeon.forgeon_api.repository.ProducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoricoProducaoService {

    private final HistoricoProducaoRepository repository;
    private final ProducaoRepository producaoRepository;

    public HistoricoProducaoService(HistoricoProducaoRepository repository, ProducaoRepository producaoRepository) {
        this.repository = repository;
        this.producaoRepository = producaoRepository;
    }

    public List<HistoricoProducaoResponse> listarPorProducao(UUID producaoId) {
        return repository.findByProducao_IdOrderByDataAsc(producaoId)
                .stream()
                .map(h -> new HistoricoProducaoResponse(
                        h.getId(),
                        h.getStatus(),
                        h.getData()
                ))
                .collect(Collectors.toList());
    }

    public HistoricoProducaoResponse criar(HistoricoProducaoRequest dto) {
        Producao producao = producaoRepository.findById(dto.getProducaoId())
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        HistoricoProducao h = new HistoricoProducao();
        h.setProducao(producao);
        h.setStatus(dto.getStatus());

        repository.save(h);

        return new HistoricoProducaoResponse(h.getId(), h.getStatus(), h.getData());
    }
}
