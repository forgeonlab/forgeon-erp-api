package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.impressora.ImpressoraRequest;
import app.forgeon.forgeon_api.dto.impressora.ImpressoraResponse;
import app.forgeon.forgeon_api.enums.StatusImpressora;
import app.forgeon.forgeon_api.model.Impressora;
import app.forgeon.forgeon_api.repository.ImpressoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImpressoraService {

    private final ImpressoraRepository repository;

    public ImpressoraService(ImpressoraRepository repository) {
        this.repository = repository;
    }

    public List<ImpressoraResponse> listarPorEmpresa(Long empresaId) {
        return repository.findByEmpresaId(empresaId).stream()
                .map(i -> new ImpressoraResponse(
                        i.getId(),
                        i.getNome(),
                        i.getModelo(),
                        i.getStatus(),
                        i.getAtivo()
                ))
                .collect(Collectors.toList());
    }

    public ImpressoraResponse criar(ImpressoraRequest dto) {
        Impressora i = new Impressora();
        i.setEmpresaId(dto.getEmpresaId());
        i.setNome(dto.getNome());
        i.setModelo(dto.getModelo());
        i.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusImpressora.DISPONIVEL);
        i.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        repository.save(i);

        return toResponse(i);
    }

    public ImpressoraResponse atualizar(Long id, ImpressoraRequest dto) {
        Impressora i = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impressora não encontrada"));

        i.setNome(dto.getNome());
        i.setModelo(dto.getModelo());
        i.setStatus(dto.getStatus());
        i.setAtivo(dto.getAtivo());
        repository.save(i);

        return toResponse(i);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private ImpressoraResponse toResponse(Impressora i) {
        return new ImpressoraResponse(i.getId(), i.getNome(), i.getModelo(), i.getStatus(), i.getAtivo());
    }
}
