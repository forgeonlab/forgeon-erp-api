package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.empresa.ConfiguracaoEmpresaCreateDTO;
import app.forgeon.forgeon_api.dto.empresa.ConfiguracaoEmpresaDTO;
import app.forgeon.forgeon_api.service.ConfiguracaoEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracoes")
public class ConfiguracaoEmpresaController {

    @Autowired
    private ConfiguracaoEmpresaService configuracaoService;

    // ✅ Listar todas as configurações de uma empresa
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ConfiguracaoEmpresaDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<ConfiguracaoEmpresaDTO> configs = configuracaoService.listarPorEmpresa(empresaId);
        return ResponseEntity.ok(configs);
    }

    // 🔍 Buscar configuração específica (por chave)
    @GetMapping("/empresa/{empresaId}/chave/{chave}")
    public ResponseEntity<ConfiguracaoEmpresaDTO> buscarPorChave(
            @PathVariable Long empresaId,
            @PathVariable String chave) {
        ConfiguracaoEmpresaDTO dto = configuracaoService.buscarPorChave(empresaId, chave);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // ➕ Criar nova configuração
    @PostMapping
    public ResponseEntity<ConfiguracaoEmpresaDTO> criar(@RequestBody ConfiguracaoEmpresaCreateDTO dto) {
        ConfiguracaoEmpresaDTO nova = configuracaoService.criar(dto);
        return ResponseEntity.ok(nova);
    }

    // ✏️ Atualizar uma configuração existente
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracaoEmpresaDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ConfiguracaoEmpresaCreateDTO dto) {
        ConfiguracaoEmpresaDTO atualizada = configuracaoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    // ❌ Deletar configuração
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        configuracaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
