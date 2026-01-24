package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.filamento.FilamentoRequest;
import app.forgeon.forgeon_api.dto.filamento.FilamentoResponse;
import app.forgeon.forgeon_api.model.Filamento;
import app.forgeon.forgeon_api.repository.FilamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilamentoService {

    private final FilamentoRepository repository;

    public FilamentoService(FilamentoRepository repository) {
        this.repository = repository;
    }

    public List<FilamentoResponse> listarPorEmpresa(UUID empresaId) {
        return repository.findByEmpresaId(empresaId).stream()
                .map(f -> new FilamentoResponse(
                        f.getId(),
                        f.getSku(),
                        f.getMarca(),
                        f.getMaterial(),
                        f.getCor(),
                        f.getPesoAtual(),
                        f.getPesoInicial(),
                        f.getCustoRolo(),
                        f.getAtivo()
                ))
                .collect(Collectors.toList());
    }

    public FilamentoResponse criar(FilamentoRequest dto) {
        Filamento f = new Filamento();
        f.setEmpresaId(dto.getEmpresaId());
        f.setSku(dto.getSku());
        f.setMarca(dto.getMarca());
        f.setMaterial(dto.getMaterial());
        f.setCor(dto.getCor());
        f.setPesoInicial(dto.getPesoInicial());
        f.setPesoAtual(dto.getPesoAtual());
        f.setCustoRolo(dto.getCustoRolo());
        f.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        repository.save(f);
        return toResponse(f);
    }

    public FilamentoResponse atualizar(UUID id, FilamentoRequest dto) {
        Filamento f = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filamento não encontrado"));

        f.setMarca(dto.getMarca());
        f.setMaterial(dto.getMaterial());
        f.setCor(dto.getCor());
        f.setPesoInicial(dto.getPesoInicial());
        f.setPesoAtual(dto.getPesoAtual());
        f.setCustoRolo(dto.getCustoRolo());
        f.setAtivo(dto.getAtivo());

        repository.save(f);
        return toResponse(f);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    private FilamentoResponse toResponse(Filamento f) {
        return new FilamentoResponse(
                f.getId(),
                f.getSku(),
                f.getMarca(),
                f.getMaterial(),
                f.getCor(),
                f.getPesoAtual(),
                f.getPesoInicial(),
                f.getCustoRolo(),
                f.getAtivo()
        );
    }
}
