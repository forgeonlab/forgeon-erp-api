package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.manutencao.ManutencaoRequest;
import app.forgeon.forgeon_api.dto.manutencao.ManutencaoResponse;
import app.forgeon.forgeon_api.service.ManutencaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
@CrossOrigin(origins = "*")
public class ManutencaoController {

    private final ManutencaoService service;

    public ManutencaoController(ManutencaoService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ManutencaoResponse>> listar(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponse> criar(@RequestBody ManutencaoRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
