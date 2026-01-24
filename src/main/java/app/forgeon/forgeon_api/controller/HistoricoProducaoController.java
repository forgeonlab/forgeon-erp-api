package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.historico.HistoricoProducaoRequest;
import app.forgeon.forgeon_api.dto.historico.HistoricoProducaoResponse;
import app.forgeon.forgeon_api.service.HistoricoProducaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/historico-producao")
@CrossOrigin(origins = "*")
public class HistoricoProducaoController {

    private final HistoricoProducaoService service;

    public HistoricoProducaoController(HistoricoProducaoService service) {
        this.service = service;
    }

    @GetMapping("/producao/{producaoId}")
    public ResponseEntity<List<HistoricoProducaoResponse>> listarPorProducao(@PathVariable UUID producaoId) {
        return ResponseEntity.ok(service.listarPorProducao(producaoId));
    }

    @PostMapping
    public ResponseEntity<HistoricoProducaoResponse> criar(@RequestBody HistoricoProducaoRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }
}
