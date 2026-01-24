package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.consumo.ConsumoFilamentoRequest;
import app.forgeon.forgeon_api.dto.consumo.ConsumoFilamentoResponse;
import app.forgeon.forgeon_api.service.ConsumoFilamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consumo-filamento")
@CrossOrigin(origins = "*")
public class ConsumoFilamentoController {

    private final ConsumoFilamentoService service;

    public ConsumoFilamentoController(ConsumoFilamentoService service) {
        this.service = service;
    }

    @GetMapping("/producao/{producaoId}")
    public ResponseEntity<List<ConsumoFilamentoResponse>> listarPorProducao(@PathVariable UUID producaoId) {
        return ResponseEntity.ok(service.listarPorProducao(producaoId));
    }

    @PostMapping
    public ResponseEntity<ConsumoFilamentoResponse> criar(@RequestBody ConsumoFilamentoRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
