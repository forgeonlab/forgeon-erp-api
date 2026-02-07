package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.venda.VendaRequest;
import app.forgeon.forgeon_api.dto.venda.VendaResponse;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    /* =========================
       LISTAR VENDAS
    ========================= */
    @GetMapping
    public ResponseEntity<List<VendaResponse>> listar() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                vendaService.listarPorEmpresa(
                        auth.getEmpresaPublicId()
                )
        );
    }

    /* =========================
       CRIAR VENDA
    ========================= */
    @PostMapping
    public ResponseEntity<VendaResponse> criar(
            @RequestBody @Valid VendaRequest dto
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.status(201).body(
                vendaService.criar(
                        dto,
                        auth.getEmpresaPublicId(),
                        auth.getUsuarioPublicId()
                )
        );
    }

    /* =========================
       ATUALIZAR STATUS
    ========================= */
    @PutMapping("/{publicId}/status")
    public ResponseEntity<VendaResponse> atualizarStatus(
            @PathVariable UUID publicId,
            @RequestParam StatusVenda status
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                vendaService.atualizarStatus(
                        publicId,
                        status,
                        auth.getEmpresaPublicId()
                )
        );
    }

    /* =========================
       DELETAR VENDA
    ========================= */
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deletar(@PathVariable UUID publicId) {

        AuthContext auth = AuthContextHolder.get();

        vendaService.deletar(
                publicId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.noContent().build();
    }
}
