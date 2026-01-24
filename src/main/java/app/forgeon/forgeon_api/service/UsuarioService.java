package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.usuario.UsuarioRequestDTO;
import app.forgeon.forgeon_api.dto.usuario.UsuarioResponseDTO;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 🔹 Criar novo usuário
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada."));

        Usuario usuario = new Usuario();
        usuario.setEmpresa(empresa);
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // 🔒 senha segura
        usuario.setPapel(dto.getPapel());
        usuario.setAtivo(dto.getAtivo());

        Usuario salvo = usuarioRepository.save(usuario);
        return mapToResponse(salvo);
    }

    // 🔹 Listar todos
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Buscar por ID
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return mapToResponse(usuario);
    }

    // 🔹 Atualizar dados (menos a senha)
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setPapel(dto.getPapel());
        usuario.setAtivo(dto.getAtivo());

        Usuario atualizado = usuarioRepository.save(usuario);
        return mapToResponse(atualizado);
    }

    // 🔹 Alterar senha
    public void alterarSenha(Long id, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    // 🔹 Deletar
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

    // 🔹 Converter entidade -> DTO
    private UsuarioResponseDTO mapToResponse(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setEmpresaId(usuario.getEmpresa().getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setPapel(usuario.getPapel());
        dto.setAtivo(usuario.getAtivo());
        dto.setDataCriacao(usuario.getDataCriacao());
        return dto;
    }
}
