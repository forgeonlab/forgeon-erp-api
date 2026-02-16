package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.pedido.CriarPedidoRequestDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoDetalhadoResponseDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoListResponseDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoResponseDTO;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.PedidoQueryService;
import app.forgeon.forgeon_api.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoQueryService pedidoQueryService;

    @GetMapping
    public ResponseEntity<Page<PedidoListResponseDTO>> listar(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        AuthContext auth = AuthContextHolder.get();
        return ResponseEntity.ok(
                pedidoQueryService.listar(auth.getEmpresaPublicId(), pageable)
        );
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<PedidoDetalhadoResponseDTO> buscarDetalhe(
            @PathVariable UUID publicId
    ) {
        AuthContext auth = AuthContextHolder.get();
        return ResponseEntity.ok(
                pedidoQueryService.buscarDetalhe(publicId, auth.getEmpresaPublicId())
        );
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(
            @RequestBody @Valid CriarPedidoRequestDTO request
    ) {
        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                pedidoService.criarPedido(
                        request,
                        auth.getEmpresaPublicId(),
                        auth.getUsuarioPublicId()
                )
        );
    }

    @PostMapping("/{publicId}/faturar")
    public ResponseEntity<Void> faturar(@PathVariable UUID publicId) {
        AuthContext auth = AuthContextHolder.get();
        pedidoService.faturar(publicId, auth.getEmpresaPublicId(), auth.getUsuarioPublicId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{publicId}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID publicId) {
        AuthContext auth = AuthContextHolder.get();
        pedidoService.cancelar(publicId, auth.getEmpresaPublicId(), auth.getUsuarioPublicId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{publicId}/confirmar")
    public ResponseEntity<Void> confirmar(@PathVariable UUID publicId) {
        AuthContext auth = AuthContextHolder.get();

        pedidoService.confirmarPagamento(
                publicId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{publicId}/em-rota")
    public ResponseEntity<Void> emRota(@PathVariable UUID publicId) {
        AuthContext auth = AuthContextHolder.get();
        pedidoService.marcarEmRota(publicId, auth.getEmpresaPublicId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{publicId}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable UUID publicId) {
        AuthContext auth = AuthContextHolder.get();
        pedidoService.finalizar(publicId, auth.getEmpresaPublicId());
        return ResponseEntity.ok().build();
    }

}
