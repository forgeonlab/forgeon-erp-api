package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoFilamentoRequest;
import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoFilamentoResponse;
import app.forgeon.forgeon_api.service.MovimentacaoFilamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes-filamento")
@CrossOrigin(origins = "*")
public class MovimentacaoFilamentoController {

    private final MovimentacaoFilamentoService service;

    public MovimentacaoFilamentoController(MovimentacaoFilamentoService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<MovimentacaoFilamentoResponse>> listar(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<MovimentacaoFilamentoResponse> registrar(@RequestBody MovimentacaoFilamentoRequest dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
