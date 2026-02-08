package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.estoque.*;
import app.forgeon.forgeon_api.model.Produto;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.EstoqueService;
import app.forgeon.forgeon_api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final ProdutoService produtoService;

    @PostMapping("/entrada")
    public ResponseEntity<Void> entrada(
            @RequestBody @Valid EntradaEstoqueRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                request.produtoPublicId(),
                auth.getEmpresaPublicId()
        );

        estoqueService.entrada(
                produto,
                request.quantidade(),
                request.referencia(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/saida")
    public ResponseEntity<Void> saida(
            @RequestBody @Valid SaidaEstoqueRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                request.produtoPublicId(),
                auth.getEmpresaPublicId()
        );

        estoqueService.saida(
                produto,
                request.quantidade(),
                request.referencia(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/ajuste")
    public ResponseEntity<Void> ajuste(
            @RequestBody @Valid AjusteEstoqueRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                request.produtoPublicId(),
                auth.getEmpresaPublicId()
        );

        estoqueService.ajuste(
                produto,
                request.quantidadeFinal(),
                request.referencia(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{produtoPublicId}")
    public ResponseEntity<EstoqueResponseDTO> consultar(
            @PathVariable UUID produtoPublicId
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                produtoPublicId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok(
                estoqueService.consultarEstoque(produto)
        );
    }

    @GetMapping("/{produtoPublicId}/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoqueResponseDTO>> movimentacoes(
            @PathVariable UUID produtoPublicId
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                produtoPublicId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok(
                estoqueService.listarMovimentacoes(produto)
        );
    }

    @PostMapping("/reserva")
    public ResponseEntity<Void> reservar(
            @RequestBody @Valid ReservaEstoqueRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                request.produtoPublicId(),
                auth.getEmpresaPublicId()
        );

        estoqueService.reservar(
                produto,
                request.quantidade(),
                request.referencia(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancelar-reserva")
    public ResponseEntity<Void> cancelarReserva(
            @RequestBody @Valid ReservaEstoqueRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        Produto produto = produtoService.buscarEntidadePorPublicId(
                request.produtoPublicId(),
                auth.getEmpresaPublicId()
        );

        estoqueService.cancelarReserva(
                produto,
                request.quantidade(),
                request.referencia(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<EstoqueDashboardDTO> dashboard() {
        return ResponseEntity.ok(
                estoqueService.dashboard()
        );
    }

}
