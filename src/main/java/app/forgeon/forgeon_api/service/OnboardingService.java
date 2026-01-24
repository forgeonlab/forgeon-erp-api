package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.onboarding.OnboardingRequestDTO;
import app.forgeon.forgeon_api.enums.PapelUsuario;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OnboardingService {
    @Autowired
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void criarEmpresaEAdmin(OnboardingRequestDTO dto) {

        if (empresaRepository.existsByCnpj(dto.cnpj())) {
            throw new RuntimeException("Empresa já cadastrada");
        }

        if (usuarioRepository.existsByEmail(dto.emailAdmin())) {
            throw new RuntimeException("Email já utilizado");
        }

        Empresa empresa = new Empresa();
        empresa.setNome(dto.nomeEmpresa());
        empresa.setCnpj(dto.cnpj());
        empresa.setEmail(dto.emailEmpresa());
        empresa.setTelefone(dto.telefoneEmpresa());

        empresaRepository.save(empresa);

        Usuario admin = new Usuario();
        admin.setPublicId(UUID.randomUUID());
        admin.setNome(dto.nomeAdmin());
        admin.setEmail(dto.emailAdmin());
        admin.setSenha(passwordEncoder.encode(dto.senhaAdmin()));
        admin.setAtivo(true);
        admin.setEmpresa(empresa);
        admin.setPapel(PapelUsuario.ADMIN);

        usuarioRepository.save(admin);
    }
}
