package app.forgeon.forgeon_api.controller.auth;

import app.forgeon.forgeon_api.dto.auth.LoginRequestDTO;
import app.forgeon.forgeon_api.dto.auth.LoginResponseDTO;
import app.forgeon.forgeon_api.dto.auth.RefreshTokenRequestDTO;
import app.forgeon.forgeon_api.model.auth.RefreshTokenRequest;
import app.forgeon.forgeon_api.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO request
    ) {
        return ResponseEntity.ok(
                authService.login(request.email(), request.senha())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(
            @RequestBody RefreshTokenRequestDTO request
    ) {
        return ResponseEntity.ok(
                authService.refresh(request.refreshToken())
        );
    }



}

