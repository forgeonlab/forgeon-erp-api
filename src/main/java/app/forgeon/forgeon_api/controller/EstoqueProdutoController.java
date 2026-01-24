package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.estoque.EstoqueProdutoRequest;
import app.forgeon.forgeon_api.dto.estoque.EstoqueProdutoResponse;
import app.forgeon.forgeon_api.service.EstoqueProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@CrossOrigin(origins = "*")
public class EstoqueProdutoController {

    private final EstoqueProdutoService service;

    public EstoqueProdutoController(EstoqueProdutoService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<EstoqueProdutoResponse>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<EstoqueProdutoResponse> criarOuAtualizar(@RequestBody EstoqueProdutoRequest dto) {
        return ResponseEntity.ok(service.criarOuAtualizar(dto));
    }

    @PatchMapping("/ajustar")
    public ResponseEntity<Void> ajustarQuantidade(
            @RequestParam Long empresaId,
            @RequestParam Long produtoId,
            @RequestParam Integer delta
    ) {
        service.ajustarQuantidade(empresaId, produtoId, delta);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
