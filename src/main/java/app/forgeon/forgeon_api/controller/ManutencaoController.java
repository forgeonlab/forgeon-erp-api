package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.manutencao.ManutencaoRequest;
import app.forgeon.forgeon_api.dto.manutencao.ManutencaoResponse;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.ManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manutencoes")
@RequiredArgsConstructor
public class ManutencaoController {

    private final ManutencaoService service;

    /* ================= LISTAR ================= */

    @GetMapping
    public ResponseEntity<List<ManutencaoResponse>> listar() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.listarPorEmpresa(auth.getEmpresaPublicId())
        );
    }

    /* ================= CRIAR ================= */

    @PostMapping
    public ResponseEntity<ManutencaoResponse> criar(
            @RequestBody @Valid ManutencaoRequest request
    ) {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.criar(
                        request,
                        auth.getEmpresaPublicId()
                )
        );
    }

    /* ================= DELETAR ================= */

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID publicId
    ) {

        AuthContext auth = AuthContextHolder.get();

        service.deletar(
                publicId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.noContent().build();
    }
}
