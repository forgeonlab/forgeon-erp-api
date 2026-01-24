package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.producao.ProducaoRequest;
import app.forgeon.forgeon_api.dto.producao.ProducaoResponse;
import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.service.ProducaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producoes")
@CrossOrigin(origins = "*")
public class ProducaoController {

    private final ProducaoService service;

    public ProducaoController(ProducaoService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ProducaoResponse>> listar(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<ProducaoResponse> criar(@RequestBody ProducaoRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}/status/{novoStatus}")
    public ResponseEntity<ProducaoResponse> atualizarStatus(
            @PathVariable Long id,
            @PathVariable StatusProducao novoStatus
    ) {
        return ResponseEntity.ok(service.atualizarStatus(id, novoStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
