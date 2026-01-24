package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.consumo.ConsumoFilamentoRequest;
import app.forgeon.forgeon_api.dto.consumo.ConsumoFilamentoResponse;
import app.forgeon.forgeon_api.model.ConsumoFilamento;
import app.forgeon.forgeon_api.model.Filamento;
import app.forgeon.forgeon_api.model.Producao;
import app.forgeon.forgeon_api.repository.ConsumoFilamentoRepository;
import app.forgeon.forgeon_api.repository.FilamentoRepository;
import app.forgeon.forgeon_api.repository.ProducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsumoFilamentoService {

    private final ConsumoFilamentoRepository repository;
    private final ProducaoRepository producaoRepository;
    private final FilamentoRepository filamentoRepository;

    public ConsumoFilamentoService(ConsumoFilamentoRepository repository,
                                   ProducaoRepository producaoRepository,
                                   FilamentoRepository filamentoRepository) {
        this.repository = repository;
        this.producaoRepository = producaoRepository;
        this.filamentoRepository = filamentoRepository;
    }

    public List<ConsumoFilamentoResponse> listarPorProducao(UUID producaoId) {
        return repository.findByProducao_Id(producaoId)
                .stream()
                .map(c -> new ConsumoFilamentoResponse(
                        c.getId(),
                        "Produção #" + c.getProducao().getId(),
                        c.getFilamento().getMarca() + " " + c.getFilamento().getCor(),
                        c.getPesoUsado()
                ))
                .collect(Collectors.toList());
    }

    public ConsumoFilamentoResponse criar(ConsumoFilamentoRequest dto) {
        Producao producao = producaoRepository.findById(dto.getProducaoId())
                .orElseThrow(() -> new RuntimeException("Produção não encontrada"));

        Filamento filamento = filamentoRepository.findById(dto.getFilamentoId())
                .orElseThrow(() -> new RuntimeException("Filamento não encontrado"));

        ConsumoFilamento c = new ConsumoFilamento();
        c.setProducao(producao);
        c.setFilamento(filamento);
        c.setPesoUsado(dto.getPesoUsado());

        repository.save(c);

        return new ConsumoFilamentoResponse(
                c.getId(),
                "Produção #" + producao.getId(),
                filamento.getMarca() + " " + filamento.getCor(),
                c.getPesoUsado()
        );
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
