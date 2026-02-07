package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.onboarding.OnboardingRequestDTO;
import app.forgeon.forgeon_api.enums.PapelUsuario;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OnboardingService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void criarEmpresaEAdmin(OnboardingRequestDTO dto) {

        var empresaDTO = dto.empresa();
        var usuarioDTO = dto.usuario();

        if (empresaRepository.existsByCnpj(empresaDTO.cnpj())) {
            throw new RuntimeException("Empresa já cadastrada");
        }

        if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new RuntimeException("Email já utilizado");
        }

        Empresa empresa = new Empresa();
        empresa.setPublicId(UUID.randomUUID());
        empresa.setNome(empresaDTO.nome());
        empresa.setCnpj(empresaDTO.cnpj());
        empresa.setEmail(empresaDTO.email());
        empresa.setTelefone(empresaDTO.telefone());
        empresa.setAtiva(true);

        empresaRepository.save(empresa);

        Usuario admin = new Usuario();
        admin.setPublicId(UUID.randomUUID());
        admin.setNome(usuarioDTO.nome());
        admin.setEmail(usuarioDTO.email());
        admin.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
        admin.setAtivo(true);
        admin.setEmpresa(empresa);
        admin.setPapel(PapelUsuario.ADMIN);

        usuarioRepository.save(admin);
    }

}
