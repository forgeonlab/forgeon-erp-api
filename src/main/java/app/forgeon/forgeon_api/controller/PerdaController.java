package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.perda.PerdaRequest;
import app.forgeon.forgeon_api.dto.perda.PerdaResponse;
import app.forgeon.forgeon_api.service.PerdaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/perdas")
@CrossOrigin(origins = "*")
public class PerdaController {

    private final PerdaService service;

    public PerdaController(PerdaService service) {
        this.service = service;
    }

    @GetMapping("/producao/{producaoId}")
    public ResponseEntity<List<PerdaResponse>> listarPorProducao(@PathVariable UUID producaoId) {
        return ResponseEntity.ok(service.listarPorProducao(producaoId));
    }

    @PostMapping
    public ResponseEntity<PerdaResponse> criar(@RequestBody PerdaRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
