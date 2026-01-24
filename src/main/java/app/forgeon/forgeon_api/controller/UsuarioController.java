package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.usuario.UsuarioRequestDTO;
import app.forgeon.forgeon_api.dto.usuario.UsuarioResponseDTO;
import app.forgeon.forgeon_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // 🌐 libera acesso ao front
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Criar novo usuário
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO novo = usuarioService.criar(dto);
        return ResponseEntity.ok(novo);
    }

    // ✅ Listar todos
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> lista = usuarioService.listar();
        return ResponseEntity.ok(lista);
    }

    // ✅ Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // ✅ Atualizar (menos senha)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO atualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // ✅ Alterar senha
    @PatchMapping("/{id}/senha")
    public ResponseEntity<String> alterarSenha(
            @PathVariable Long id,
            @RequestBody String novaSenha
    ) {
        usuarioService.alterarSenha(id, novaSenha);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }

    // ✅ Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}
