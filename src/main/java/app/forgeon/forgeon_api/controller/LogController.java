package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.log.LogRequest;
import app.forgeon.forgeon_api.dto.log.LogResponse;
import app.forgeon.forgeon_api.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogController {

    private final LogService service;

    public LogController(LogService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<LogResponse>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<LogResponse> registrar(@RequestBody LogRequest dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
