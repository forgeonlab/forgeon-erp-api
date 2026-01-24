package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.manutencao.ManutencaoRequest;
import app.forgeon.forgeon_api.dto.manutencao.ManutencaoResponse;
import app.forgeon.forgeon_api.model.Impressora;
import app.forgeon.forgeon_api.model.Manutencao;
import app.forgeon.forgeon_api.repository.ImpressoraRepository;
import app.forgeon.forgeon_api.repository.ManutencaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManutencaoService {

    private final ManutencaoRepository repository;
    private final ImpressoraRepository impressoraRepository;

    public ManutencaoService(ManutencaoRepository repository, ImpressoraRepository impressoraRepository) {
        this.repository = repository;
        this.impressoraRepository = impressoraRepository;
    }

    public List<ManutencaoResponse> listarPorEmpresa(Long empresaId) {
        return repository.findByImpressora_EmpresaIdOrderByDataDesc(empresaId)
                .stream()
                .map(m -> new ManutencaoResponse(
                        m.getId(),
                        m.getImpressora().getNome(),
                        m.getTipo(),
                        m.getDescricao(),
                        m.getCusto(),
                        m.getData()
                ))
                .collect(Collectors.toList());
    }

    public ManutencaoResponse criar(ManutencaoRequest dto) {
        Impressora impressora = impressoraRepository.findById(dto.getImpressoraId())
                .orElseThrow(() -> new RuntimeException("Impressora não encontrada"));

        Manutencao m = new Manutencao();
        m.setImpressora(impressora);
        m.setTipo(dto.getTipo());
        m.setDescricao(dto.getDescricao());
        m.setCusto(dto.getCusto());

        repository.save(m);

        return new ManutencaoResponse(
                m.getId(),
                impressora.getNome(),
                m.getTipo(),
                m.getDescricao(),
                m.getCusto(),
                m.getData()
        );
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
