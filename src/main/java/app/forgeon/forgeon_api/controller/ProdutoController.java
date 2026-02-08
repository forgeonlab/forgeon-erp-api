package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.produto.*;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDetalhadoResponseDTO> criarProduto(
            @RequestBody @Valid ProdutoCreateFullRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        ProdutoDetalhadoResponseDTO response = produtoService.criarProduto(
                request.produto(),
                request.producao(),
                request.precos(),
                auth.getEmpresaPublicId(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                produtoService.listarPorEmpresa(auth.getEmpresaPublicId())
        );
    }


    @GetMapping("/{publicId}")
    public ResponseEntity<ProdutoDetalhadoResponseDTO> buscarProduto(
            @PathVariable UUID publicId
    ) {
        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                produtoService.buscarPorPublicId(publicId, auth.getEmpresaPublicId())
        );
    }


    @PutMapping("/{publicId}")
    public ResponseEntity<ProdutoDetalhadoResponseDTO> atualizarProduto(
            @PathVariable UUID publicId,
            @RequestBody @Valid ProdutoUpdateFullRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                produtoService.atualizarProduto(
                        publicId,
                        request,
                        auth.getEmpresaPublicId()
                )
        );
    }



}
