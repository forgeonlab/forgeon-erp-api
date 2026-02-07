package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.cliente.ClienteCreateDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteEstatisticasDTO;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /* =========================
       LISTAR CLIENTES
    ========================= */
    @GetMapping
    public Page<ClienteDTO> listar(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        AuthContext auth = AuthContextHolder.get();

        return clienteService.listar(
                ativo,
                search,
                pageable,
                auth.getEmpresaPublicId()
        );
    }

    /* =========================
       BUSCAR CLIENTE
    ========================= */
    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable UUID id) {
        AuthContext auth = AuthContextHolder.get();

        return clienteService.buscarPorId(
                id,
                auth.getEmpresaPublicId()
        );
    }

    /* =========================
       CRIAR CLIENTE
    ========================= */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO criar(@Valid @RequestBody ClienteCreateDTO dto) {
        AuthContext auth = AuthContextHolder.get();

        return clienteService.criar(
                dto,
                auth.getEmpresaPublicId(),
                auth.getUsuarioPublicId()
        );
    }

    /* =========================
       ATUALIZAR CLIENTE
    ========================= */
    @PutMapping("/{id}")
    public ClienteDTO atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ClienteCreateDTO dto
    ) {
        AuthContext auth = AuthContextHolder.get();

        return clienteService.atualizar(
                id,
                dto,
                auth.getEmpresaPublicId()
        );
    }

    /* =========================
       ATIVAR / DESATIVAR
    ========================= */
    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarStatus(
            @PathVariable UUID id,
            @RequestParam Boolean ativo
    ) {
        AuthContext auth = AuthContextHolder.get();

        clienteService.alterarStatus(
                id,
                ativo,
                auth.getEmpresaPublicId()
        );
    }

    /* =========================
       ESTATÍSTICAS
    ========================= */
    @GetMapping("/estatisticas")
    public ClienteEstatisticasDTO estatisticas() {
        AuthContext auth = AuthContextHolder.get();

        return clienteService.estatisticas(
                auth.getEmpresaPublicId()
        );
    }
}
