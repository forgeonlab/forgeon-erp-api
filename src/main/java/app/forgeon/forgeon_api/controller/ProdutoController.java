package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.produto.ProdutoRequest;
import app.forgeon.forgeon_api.dto.produto.ProdutoResponse;
import app.forgeon.forgeon_api.dto.produto.ProdutoVendaDTO;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /* =========================
       LISTAR PRODUTOS
    ========================= */
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                produtoService.listarPorEmpresa(
                        auth.getEmpresaPublicId()
                )
        );
    }

    /* =========================
       CRIAR PRODUTO
    ========================= */
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(
            @RequestBody @Valid ProdutoRequest dto
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.status(201).body(
                produtoService.criar(
                        dto,
                        auth.getEmpresaPublicId(),
                        auth.getUsuarioPublicId()
                )
        );
    }

    /* =========================
       ATUALIZAR PRODUTO
    ========================= */
    @PutMapping("/{publicId}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable UUID publicId,
            @RequestBody @Valid ProdutoRequest dto
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                produtoService.atualizar(
                        publicId,
                        dto,
                        auth.getEmpresaPublicId()
                )
        );
    }

    /* =========================
       DELETAR PRODUTO
    ========================= */
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deletar(@PathVariable UUID publicId) {

        AuthContext auth = AuthContextHolder.get();

        produtoService.deletar(
                publicId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.noContent().build();
    }

    /* =========================
       VENDAS POR PRODUTO
    ========================= */
    @GetMapping("/vendas")
    public ResponseEntity<List<ProdutoVendaDTO>> listarVendasPorProduto() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                produtoService.listarVendasPorProduto(
                        auth.getEmpresaPublicId()
                )
        );
    }
}
