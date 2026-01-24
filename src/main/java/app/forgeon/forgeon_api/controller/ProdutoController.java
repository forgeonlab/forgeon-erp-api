package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.produto.ProdutoRequest;
import app.forgeon.forgeon_api.dto.produto.ProdutoResponse;
import app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO;
import app.forgeon.forgeon_api.repository.VendaRepository;
import app.forgeon.forgeon_api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService service;
    @Autowired
    private final VendaRepository vendaRepository;

    public ProdutoController(ProdutoService service, VendaRepository venda) {
        this.service = service;
        this.vendaRepository = venda;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ProdutoResponse>> listar(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @RequestBody ProdutoRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}/vendas")
    public List<ProdutoVendaDTO> teste(@PathVariable Long empresaId) {
        return vendaRepository.vendasPorProduto(empresaId);
    }

}
