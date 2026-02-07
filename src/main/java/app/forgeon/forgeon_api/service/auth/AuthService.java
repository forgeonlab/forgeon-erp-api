package app.forgeon.forgeon_api.service.auth;

import app.forgeon.forgeon_api.dto.auth.LoginResponseDTO;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.model.auth.RefreshToken;
import app.forgeon.forgeon_api.repository.UsuarioRepository;
import app.forgeon.forgeon_api.repository.auth.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(
            JwtService jwtService,
            UsuarioRepository usuarioRepository,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Transactional
    public LoginResponseDTO login(String email, String senha) {

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        // validar senha...

        String accessToken = jwtService.gerarAccessToken(usuario);
        String refreshToken = jwtService.gerarRefreshToken(usuario);

        refreshTokenRepository.deleteByUsuarioId(usuario.getId());

        refreshTokenRepository.save(
                new RefreshToken(usuario, refreshToken)
        );

        return new LoginResponseDTO(
                accessToken,
                refreshToken,
                jwtService.getAccessTokenExpiration()
        );
    }

    @Transactional
    public LoginResponseDTO refresh(String refreshToken) {

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        Usuario usuario = token.getUsuario();

        String newAccessToken = jwtService.gerarAccessToken(usuario);
        String newRefreshToken = jwtService.gerarRefreshToken(usuario);

        token.setToken(newRefreshToken);
        refreshTokenRepository.save(token);

        return new LoginResponseDTO(
                newAccessToken,
                newRefreshToken,
                jwtService.getAccessTokenExpiration()
        );
    }

}
