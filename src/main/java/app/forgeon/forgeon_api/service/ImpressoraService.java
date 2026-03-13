package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.impressora.ImpressoraRequest;
import app.forgeon.forgeon_api.dto.impressora.ImpressoraResponse;
import app.forgeon.forgeon_api.enums.StatusImpressora;
import app.forgeon.forgeon_api.model.Impressora;
import app.forgeon.forgeon_api.repository.ImpressoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImpressoraService {

    private final ImpressoraRepository repository;

    public List<ImpressoraResponse> listarPorEmpresa(UUID empresaPublicId) {

        return repository.findByEmpresaPublicId(empresaPublicId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ImpressoraResponse criar(
            ImpressoraRequest dto,
            UUID empresaPublicId
    ) {

        Impressora i = new Impressora();

        i.setEmpresaPublicId(empresaPublicId);
        i.setNome(dto.getNome());
        i.setModelo(dto.getModelo());
        i.setStatus(dto.getStatus() != null
                ? dto.getStatus()
                : StatusImpressora.DISPONIVEL);

        i.setAtivo(dto.getAtivo() != null
                ? dto.getAtivo()
                : true);

        repository.save(i);

        return toResponse(i);
    }

    public ImpressoraResponse atualizar(
            UUID publicId,
            ImpressoraRequest dto,
            UUID empresaPublicId
    ) {

        Impressora i = repository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Impressora não encontrada"));

        i.setNome(dto.getNome());
        i.setModelo(dto.getModelo());
        i.setStatus(dto.getStatus());
        i.setAtivo(dto.getAtivo());

        repository.save(i);

        return toResponse(i);
    }

    public void deletar(
            UUID publicId,
            UUID empresaPublicId
    ) {

        Impressora i = repository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Impressora não encontrada"));

        repository.delete(i);
    }

    private ImpressoraResponse toResponse(Impressora i) {

        return new ImpressoraResponse(
                i.getPublicId(),
                i.getNome(),
                i.getModelo(),
                i.getStatus(),
                i.getAtivo()
        );
    }
}
