package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.log.LogRequest;
import app.forgeon.forgeon_api.dto.log.LogResponse;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Log;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.LogRepository;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    public LogService(
            LogRepository logRepository,
            EmpresaRepository empresaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.logRepository = logRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /* =========================
       LISTAR LOGS POR EMPRESA
       ========================= */
    public List<LogResponse> listarPorEmpresa(UUID empresaPublicId) {

        Empresa empresa = empresaRepository
                .findByPublicId(empresaPublicId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        return logRepository.findByEmpresaOrderByDataDesc(empresa)
                .stream()
                .map(l -> new LogResponse(
                        l.getId(),
                        l.getUsuario() != null ? l.getUsuario().getNome() : "Sistema",
                        l.getEntidade(),
                        l.getEntidadeId(),
                        l.getAcao(),
                        l.getData()
                ))
                .collect(Collectors.toList());
    }

    /* =========================
       REGISTRAR LOG
       ========================= */
    public LogResponse registrar(LogRequest dto) {

        Empresa empresa = empresaRepository
                .findByPublicId(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Usuario usuario = null;
        if (dto.getUsuarioId() != null) {
            usuario = usuarioRepository
                    .findByPublicId(dto.getUsuarioId())
                    .orElse(null);
        }

        Log log = new Log();
        log.setEmpresa(empresa);
        log.setUsuario(usuario);
        log.setEntidade(dto.getEntidade());
        log.setEntidadeId(dto.getEntidadeId());
        log.setAcao(dto.getAcao());

        logRepository.save(log);

        return new LogResponse(
                log.getId(),
                usuario != null ? usuario.getNome() : "Sistema",
                log.getEntidade(),
                log.getEntidadeId(),
                log.getAcao(),
                log.getData()
        );
    }

    public void deletar(UUID id) {
        logRepository.deleteById(id);
    }
}
