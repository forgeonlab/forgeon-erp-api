package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.filamento.FilamentoRequest;
import app.forgeon.forgeon_api.dto.filamento.FilamentoResponse;
import app.forgeon.forgeon_api.service.FilamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filamentos")
@CrossOrigin(origins = "*")
public class FilamentoController {

    private final FilamentoService service;

    public FilamentoController(FilamentoService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<FilamentoResponse>> listar(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<FilamentoResponse> criar(@RequestBody FilamentoRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilamentoResponse> atualizar(@PathVariable Long id, @RequestBody FilamentoRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
