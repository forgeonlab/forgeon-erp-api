package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.impressora.ImpressoraRequest;
import app.forgeon.forgeon_api.dto.impressora.ImpressoraResponse;
import app.forgeon.forgeon_api.service.ImpressoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/impressoras")
@CrossOrigin(origins = "*")
public class ImpressoraController {

    private final ImpressoraService service;

    public ImpressoraController(ImpressoraService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ImpressoraResponse>> listar(@PathVariable UUID empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<ImpressoraResponse> criar(@RequestBody ImpressoraRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImpressoraResponse> atualizar(@PathVariable UUID id, @RequestBody ImpressoraRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
