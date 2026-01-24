package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.usuario.UsuarioCreateDTO;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmpresaRepository empresaRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Usuario criar(UsuarioCreateDTO dto) {

        AuthContext auth = AuthContextHolder.get();

        if (auth == null || !auth.isAdmin()) {
            throw new RuntimeException("Apenas ADMIN pode criar usuários");
        }

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Empresa empresa = empresaRepository
                .findByPublicId(auth.getEmpresaPublicId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPapel(dto.papel());
        usuario.setAtivo(true);
        usuario.setEmpresa(empresa); // 👈 AQUI está o ajuste certo

        return usuarioRepository.save(usuario);
    }
}
