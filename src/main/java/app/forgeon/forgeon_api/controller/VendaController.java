package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.venda.VendaRequest;
import app.forgeon.forgeon_api.dto.venda.VendaResponse;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<VendaResponse>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(service.listarPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<VendaResponse> criar(@RequestBody VendaRequest dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<VendaResponse> atualizarStatus(@PathVariable Long id, @RequestParam StatusVenda status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
