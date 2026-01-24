package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.me.MeEmpresaDTO;
import app.forgeon.forgeon_api.dto.me.MeResponseDTO;
import app.forgeon.forgeon_api.dto.me.MeUsuarioDTO;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    public MeController(
            UsuarioRepository usuarioRepository,
            EmpresaRepository empresaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
    }

    @GetMapping("/me")
    public MeResponseDTO me() {

        AuthContext auth = AuthContextHolder.get();

        Usuario usuario = usuarioRepository
                .findByPublicId(auth.getUsuarioPublicId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Empresa empresa = empresaRepository
                .findByPublicId(auth.getEmpresaPublicId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        return new MeResponseDTO(
                new MeUsuarioDTO(
                        usuario.getPublicId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getPapel().name()
                ),
                new MeEmpresaDTO(
                        empresa.getPublicId(),
                        empresa.getNome()
                )
        );
    }
}
