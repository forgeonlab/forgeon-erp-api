package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.usuario.UsuarioCreateDTO;
import app.forgeon.forgeon_api.dto.usuario.UsuarioResponseDTO;
import app.forgeon.forgeon_api.model.Usuario;
import app.forgeon.forgeon_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(
            @RequestBody @Valid UsuarioCreateDTO dto
    ) {

        Usuario usuario = usuarioService.criar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UsuarioResponseDTO(usuario));
    }
}
