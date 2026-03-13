package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.manutencao.ManutencaoRequest;
import app.forgeon.forgeon_api.dto.manutencao.ManutencaoResponse;
import app.forgeon.forgeon_api.model.Impressora;
import app.forgeon.forgeon_api.model.Manutencao;
import app.forgeon.forgeon_api.repository.ImpressoraRepository;
import app.forgeon.forgeon_api.repository.ManutencaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final ManutencaoRepository repository;
    private final ImpressoraRepository impressoraRepository;

    /* ================= LISTAR ================= */

    public List<ManutencaoResponse> listarPorEmpresa(UUID empresaPublicId) {

        return repository
                .findByEmpresaPublicIdOrderByDataDesc(empresaPublicId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ================= CRIAR ================= */

    public ManutencaoResponse criar(
            ManutencaoRequest dto,
            UUID empresaPublicId
    ) {

        Impressora impressora = impressoraRepository
                .findByPublicIdAndEmpresaPublicId(
                        dto.getImpressoraPublicId(),
                        empresaPublicId
                )
                .orElseThrow(() -> new RuntimeException("Impressora não encontrada"));

        Manutencao m = new Manutencao();
        m.setEmpresaPublicId(empresaPublicId);
        m.setImpressora(impressora);
        m.setTipo(dto.getTipo());
        m.setDescricao(dto.getDescricao());
        m.setCusto(dto.getCusto());

        repository.save(m);

        return toResponse(m);
    }

    /* ================= DELETAR ================= */

    public void deletar(UUID publicId, UUID empresaPublicId) {

        Manutencao m = repository
                .findByPublicIdAndEmpresaPublicId(publicId, empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));

        repository.delete(m);
    }

    /* ================= MAPPER ================= */

    private ManutencaoResponse toResponse(Manutencao m) {

        return new ManutencaoResponse(
                m.getPublicId(),
                m.getImpressora().getNome(),
                m.getTipo(),
                m.getDescricao(),
                m.getCusto(),
                m.getData()
        );
    }
}
