package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.filamento.FilamentoRequest;
import app.forgeon.forgeon_api.dto.filamento.FilamentoResponse;
import app.forgeon.forgeon_api.exception.FilamentoNaoEncontradoException;
import app.forgeon.forgeon_api.exception.SkuDuplicadoException;
import app.forgeon.forgeon_api.model.Filamento;
import app.forgeon.forgeon_api.repository.ConsumoFilamentoRepository;
import app.forgeon.forgeon_api.repository.FilamentoRepository;
import app.forgeon.forgeon_api.repository.MovimentacaoFilamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilamentoService {

    private final FilamentoRepository repository;
    private final ConsumoFilamentoRepository consumoFilamentoRepository;
    private final MovimentacaoFilamentoRepository movimentacaoFilamentoRepository;

    public FilamentoService(
            FilamentoRepository repository,
            ConsumoFilamentoRepository consumoFilamentoRepository,
            MovimentacaoFilamentoRepository movimentacaoFilamentoRepository
    ) {
        this.repository = repository;
        this.consumoFilamentoRepository = consumoFilamentoRepository;
        this.movimentacaoFilamentoRepository = movimentacaoFilamentoRepository;
    }

    public List<FilamentoResponse> listarPorEmpresa(UUID empresaId) {
        return repository.findByEmpresaIdAndAtivoTrue(empresaId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FilamentoResponse buscarPorId(UUID id, UUID empresaId) {
        return toResponse(buscarFilamentoDaEmpresa(id, empresaId));
    }

    public FilamentoResponse criar(FilamentoRequest dto, UUID empresaId) {
        if (repository.existsByEmpresaIdAndSku(empresaId, dto.getSku())) {
            throw new SkuDuplicadoException(dto.getSku());
        }

        Filamento f = new Filamento();
        f.setEmpresaId(empresaId);
        f.setSku(dto.getSku());
        f.setMarca(dto.getMarca());
        f.setMaterial(dto.getMaterial());
        f.setCor(dto.getCor());
        f.setPesoInicial(dto.getPesoInicial());
        f.setPesoAtual(dto.getPesoAtual() != null ? dto.getPesoAtual() : dto.getPesoInicial());
        f.setCustoRolo(dto.getCustoRolo());
        f.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        repository.save(f);
        return toResponse(f);
    }

    public FilamentoResponse atualizar(UUID id, FilamentoRequest dto, UUID empresaId) {
        Filamento f = buscarFilamentoDaEmpresa(id, empresaId);

        boolean skuMudou = !f.getSku().equals(dto.getSku());
        if (skuMudou && repository.existsByEmpresaIdAndSku(f.getEmpresaId(), dto.getSku())) {
            throw new SkuDuplicadoException(dto.getSku());
        }

        f.setSku(dto.getSku());
        f.setMarca(dto.getMarca());
        f.setMaterial(dto.getMaterial());
        f.setCor(dto.getCor());
        f.setPesoInicial(dto.getPesoInicial());
        f.setPesoAtual(dto.getPesoAtual() != null ? dto.getPesoAtual() : f.getPesoAtual());
        f.setCustoRolo(dto.getCustoRolo());
        f.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : f.getAtivo());

        repository.save(f);
        return toResponse(f);
    }

    public void deletar(UUID id, UUID empresaId) {
        Filamento f = buscarFilamentoDaEmpresa(id, empresaId);

        boolean possuiVinculos =
                consumoFilamentoRepository.existsByFilamento_Id(id)
                        || movimentacaoFilamentoRepository.existsByFilamento_Id(id);

        if (possuiVinculos) {
            f.setAtivo(false);
            repository.save(f);
            return;
        }

        repository.delete(f);
    }

    private Filamento buscarFilamentoDaEmpresa(UUID id, UUID empresaId) {
        Filamento f = repository.findById(id)
                .orElseThrow(() -> new FilamentoNaoEncontradoException(id));

        if (!f.getEmpresaId().equals(empresaId)) {
            throw new FilamentoNaoEncontradoException(id);
        }

        return f;
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
