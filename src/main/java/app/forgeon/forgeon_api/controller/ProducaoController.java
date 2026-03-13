package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.producao.*;
import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.ProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/producao")
@RequiredArgsConstructor
public class ProducaoController {

    private final ProducaoService service;

    /* =====================================================
       1️⃣ LISTAR PRODUÇÕES DA EMPRESA (TOKEN)
    ===================================================== */

    @GetMapping
    public ResponseEntity<List<ProducaoResponse>> listar() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.listarPorEmpresa(auth.getEmpresaPublicId())
        );
    }

    /* =====================================================
       2️⃣ CRIAR PRODUÇÃO
    ===================================================== */

    @PostMapping
    public ResponseEntity<ProducaoResponse> criar(
            @RequestBody @Valid ProducaoRequest request
    ) {

        AuthContext auth = AuthContextHolder.get();

        ProducaoResponse response = service.criar(
                request,
                auth.getEmpresaPublicId(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.status(201).body(response);
    }

    /* =====================================================
       3️⃣ ATUALIZAR STATUS
    ===================================================== */

    @PutMapping("/{publicId}/status/{novoStatus}")
    public ResponseEntity<ProducaoResponse> atualizarStatus(
            @PathVariable UUID publicId,
            @PathVariable StatusProducao novoStatus
    ) {

        AuthContext auth = AuthContextHolder.get();

        ProducaoResponse response = service.atualizarStatus(
                publicId,
                novoStatus,
                auth.getEmpresaPublicId(),
                auth.getUsuarioPublicId()
        );

        return ResponseEntity.ok(response);
    }

    /* =====================================================
       4️⃣ ATUALIZAR QUANTIDADE BOA
    ===================================================== */

    @PatchMapping("/{publicId}/quantidade-boa")
    public ResponseEntity<ProducaoResponse> atualizarQuantidadeBoa(
            @PathVariable UUID publicId,
            @RequestBody @Valid AtualizarQuantidadeBoaRequest request
    ) {

        AuthContext auth = AuthContextHolder.get();

        ProducaoResponse response = service.atualizarQuantidadeBoa(
                publicId,
                request,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok(response);
    }

    /* =====================================================
       5️⃣ DASHBOARD
    ===================================================== */

    @GetMapping("/dashboard")
    public ResponseEntity<ProducaoDashboardDTO> dashboard() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                service.dashboard(auth.getEmpresaPublicId())
        );
    }

    /* =====================================================
       6️⃣ DELETAR PRODUÇÃO
    ===================================================== */

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
