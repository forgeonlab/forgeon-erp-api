package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.pedido.PedidoDetalhadoResponseDTO;
import app.forgeon.forgeon_api.dto.pedido.PedidoListResponseDTO;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.PedidoQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/pedidos/query")
@RequiredArgsConstructor
public class PedidoQueryController {

    private final PedidoQueryService pedidoQueryService;

    @GetMapping
    public ResponseEntity<Page<PedidoListResponseDTO>> listar(
            Pageable pageable
    ) {
        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                pedidoQueryService.listar(
                        auth.getEmpresaPublicId(),
                        pageable
                )
        );
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<PedidoDetalhadoResponseDTO> detalhe(
            @PathVariable UUID publicId
    ) {
        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                pedidoQueryService.buscarDetalhe(
                        publicId,
                        auth.getEmpresaPublicId()
                )
        );
    }
}
