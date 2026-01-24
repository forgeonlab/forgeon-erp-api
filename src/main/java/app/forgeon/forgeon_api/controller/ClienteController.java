package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.cliente.ClienteCreateDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteDTO;
import app.forgeon.forgeon_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/empresa/{empresaId}")
    public List<ClienteDTO> listarPorEmpresa(@PathVariable Long empresaId) {
        return clienteService.listarPorEmpresa(empresaId);
    }

    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    public ClienteDTO criar(@RequestBody ClienteCreateDTO dto) {
        return clienteService.criar(dto);
    }

    @PutMapping("/{id}")
    public ClienteDTO atualizar(@PathVariable Long id, @RequestBody ClienteCreateDTO dto) {
        return clienteService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteService.deletar(id);
    }

    @PatchMapping("/{id}/status")
    public void alterarStatus(@PathVariable Long id, @RequestParam Boolean ativo) {
        clienteService.alterarStatus(id, ativo);
    }
}
