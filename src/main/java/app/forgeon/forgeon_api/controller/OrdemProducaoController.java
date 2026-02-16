package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.OrdemProducaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ordens-producao")
@RequiredArgsConstructor
public class OrdemProducaoController {

    private final OrdemProducaoService ordemProducaoService;

    @PostMapping("/{ordemId}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable UUID ordemId) {

        AuthContext auth = AuthContextHolder.get();

        ordemProducaoService.iniciar(
                ordemId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ordemId}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable UUID ordemId) {

        AuthContext auth = AuthContextHolder.get();

        ordemProducaoService.finalizar(
                ordemId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ordemId}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID ordemId) {

        AuthContext auth = AuthContextHolder.get();

        ordemProducaoService.cancelar(
                ordemId,
                auth.getEmpresaPublicId()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> listarFila() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                ordemProducaoService.listarFila(auth.getEmpresaPublicId())
        );
    }
}