package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoFilamentoRequest;
import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoFilamentoResponse;
import app.forgeon.forgeon_api.model.Filamento;
import app.forgeon.forgeon_api.model.MovimentacaoFilamento;
import app.forgeon.forgeon_api.repository.FilamentoRepository;
import app.forgeon.forgeon_api.repository.MovimentacaoFilamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoFilamentoService {

    private final MovimentacaoFilamentoRepository repo;
    private final FilamentoRepository filamentoRepo;

    public MovimentacaoFilamentoService(MovimentacaoFilamentoRepository repo, FilamentoRepository filamentoRepo) {
        this.repo = repo;
        this.filamentoRepo = filamentoRepo;
    }

    public List<MovimentacaoFilamentoResponse> listarPorEmpresa(Long empresaId) {
        return repo.findByEmpresaIdOrderByDataDesc(empresaId)
                .stream()
                .map(m -> new MovimentacaoFilamentoResponse(
                        m.getId(),
                        m.getFilamento().getMarca() + " - " + m.getFilamento().getSku(),
                        m.getFilamento().getMaterial(),
                        m.getFilamento().getCor(),
                        m.getTipo(),
                        m.getQuantidade(),
                        m.getOrigem(),
                        m.getData()
                ))
                .collect(Collectors.toList());
    }

    public MovimentacaoFilamentoResponse registrar(MovimentacaoFilamentoRequest dto) {
        Filamento filamento = filamentoRepo.findById(dto.getFilamentoId())
                .orElseThrow(() -> new RuntimeException("Filamento não encontrado"));

        MovimentacaoFilamento mov = new MovimentacaoFilamento();
        mov.setEmpresaId(dto.getEmpresaId());
        mov.setFilamento(filamento);
        mov.setTipo(dto.getTipo());
        mov.setQuantidade(dto.getQuantidade());
        mov.setOrigem(dto.getOrigem());
        mov.setReferenciaId(dto.getReferenciaId());

        repo.save(mov);

        return new MovimentacaoFilamentoResponse(
                mov.getId(),
                filamento.getMarca() + " - " + filamento.getSku(),
                filamento.getMaterial(),
                filamento.getCor(),
                mov.getTipo(),
                mov.getQuantidade(),
                mov.getOrigem(),
                mov.getData()
        );
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}
