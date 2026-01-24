package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoEstoqueProdutoRequest;
import app.forgeon.forgeon_api.dto.movimentacao.MovimentacaoEstoqueProdutoResponse;
import app.forgeon.forgeon_api.service.MovimentacaoEstoqueProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimentacoes-estoque")
@CrossOrigin(origins = "*")
public class MovimentacaoEstoqueProdutoController {

    private final MovimentacaoEstoqueProdutoService service;

    public MovimentacaoEstoqueProdutoController(MovimentacaoEstoqueProdutoService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<MovimentacaoEstoqueProdutoResponse>> listar(@PathVariable UUID empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<MovimentacaoEstoqueProdutoResponse> registrar(@RequestBody MovimentacaoEstoqueProdutoRequest dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
