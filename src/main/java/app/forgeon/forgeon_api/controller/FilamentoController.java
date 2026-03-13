package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.filamento.FilamentoRequest;
import app.forgeon.forgeon_api.dto.filamento.FilamentoResponse;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.FilamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filamentos")
@CrossOrigin(origins = "*")
public class FilamentoController {

    private final FilamentoService service;

    public FilamentoController(FilamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FilamentoResponse>> listar() {
        AuthContext auth = AuthContextHolder.get();
        return ResponseEntity.ok(service.listarPorEmpresa(auth.getEmpresaPublicId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilamentoResponse> buscarPorId(@PathVariable UUID id) {
        AuthContext auth = AuthContextHolder.get();
        return ResponseEntity.ok(service.buscarPorId(id, auth.getEmpresaPublicId()));
    }

    @PostMapping
    public ResponseEntity<FilamentoResponse> criar(@RequestBody @Valid FilamentoRequest dto) {
        AuthContext auth = AuthContextHolder.get();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto, auth.getEmpresaPublicId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilamentoResponse> atualizar(@PathVariable UUID id, @RequestBody @Valid FilamentoRequest dto) {
        AuthContext auth = AuthContextHolder.get();
        return ResponseEntity.ok(service.atualizar(id, dto, auth.getEmpresaPublicId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        AuthContext auth = AuthContextHolder.get();
        service.deletar(id, auth.getEmpresaPublicId());
        return ResponseEntity.noContent().build();
    }
}
