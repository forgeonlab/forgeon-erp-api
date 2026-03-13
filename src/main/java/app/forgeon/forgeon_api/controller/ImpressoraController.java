package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.impressora.ImpressoraRequest;
import app.forgeon.forgeon_api.dto.impressora.ImpressoraResponse;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.ImpressoraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/impressoras")
@RequiredArgsConstructor
public class ImpressoraController {

    private final ImpressoraService service;

    @GetMapping
    public ResponseEntity<List<ImpressoraResponse>> listar() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.listarPorEmpresa(auth.getEmpresaPublicId())
        );
    }

    @PostMapping
    public ResponseEntity<ImpressoraResponse> criar(
            @RequestBody @Valid ImpressoraRequest request
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.criar(request, auth.getEmpresaPublicId())
        );
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<ImpressoraResponse> atualizar(
            @PathVariable UUID publicId,
            @RequestBody @Valid ImpressoraRequest request
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.atualizar(publicId, request, auth.getEmpresaPublicId())
        );
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID publicId
    ) {

        AuthContext auth = AuthContextHolder.get();

        service.deletar(publicId, auth.getEmpresaPublicId());

        return ResponseEntity.noContent().build();
    }
}
