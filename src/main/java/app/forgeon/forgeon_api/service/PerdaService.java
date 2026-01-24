package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.perda.PerdaRequest;
import app.forgeon.forgeon_api.dto.perda.PerdaResponse;
import app.forgeon.forgeon_api.model.Perda;
import app.forgeon.forgeon_api.model.Producao;
import app.forgeon.forgeon_api.repository.PerdaRepository;
import app.forgeon.forgeon_api.repository.ProducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PerdaService {

    private final PerdaRepository repository;
    private final ProducaoRepository producaoRepository;

    public PerdaService(PerdaRepository repository, ProducaoRepository producaoRepository) {
        this.repository = repository;
        this.producaoRepository = producaoRepository;
    }

    public List<PerdaResponse> listarPorProducao(UUID producaoId) {
        return repository.findByProducao_IdOrderByDataDesc(producaoId)
                .stream()
                .map(p -> new PerdaResponse(
                        p.getId(),
                        "Produção #" + p.getProducao().getId(),
                        p.getMotivo(),
                        p.getPesoPerdido(),
                        p.getData()
                ))
                .collect(Collectors.toList());
    }

    public PerdaResponse criar(PerdaRequest dto) {
        Producao producao = producaoRepository.findById(dto.getProducaoId())
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        Perda perda = new Perda();
        perda.setProducao(producao);
        perda.setMotivo(dto.getMotivo());
        perda.setPesoPerdido(dto.getPesoPerdido());

        repository.save(perda);

        return new PerdaResponse(
                perda.getId(),
                "Produção #" + producao.getId(),
                perda.getMotivo(),
                perda.getPesoPerdido(),
                perda.getData()
        );
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
