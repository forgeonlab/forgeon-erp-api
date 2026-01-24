package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.empresa.EmpresaRequestDTO;
import app.forgeon.forgeon_api.dto.empresa.EmpresaResponseDTO;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // ✅ Listar todas as empresas
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listar() {
        List<EmpresaResponseDTO> lista = empresaService.listarTodas().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // ✅ Buscar empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id)
                .map(empresa -> ResponseEntity.ok(toResponseDTO(empresa)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Criar nova empresa
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criar(@Valid @RequestBody EmpresaRequestDTO dto) {
        Empresa nova = new Empresa();
        aplicarDTO(nova, dto);
        Empresa salva = empresaService.salvar(nova);
        return ResponseEntity.ok(toResponseDTO(salva));
    }

    // ✅ Atualizar uma empresa
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody EmpresaRequestDTO dto) {
        return empresaService.atualizar(id, toEntity(dto))
                .map(atualizada -> ResponseEntity.ok(toResponseDTO(atualizada)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean removida = empresaService.deletar(id);
        return removida ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // 🔁 Conversões auxiliares (Entity ↔ DTO)
    private EmpresaResponseDTO toResponseDTO(Empresa e) {
        return new EmpresaResponseDTO(
                e.getId(),
                e.getNome(),
                e.getCnpj(),
                e.getEmail(),
                e.getTelefone(),
                e.getAtiva(),
                e.getDataCriacao()
        );
    }

    private Empresa toEntity(EmpresaRequestDTO dto) {
        Empresa e = new Empresa();
        aplicarDTO(e, dto);
        return e;
    }

    private void aplicarDTO(Empresa e, EmpresaRequestDTO dto) {
        e.setNome(dto.getNome());
        e.setCnpj(dto.getCnpj());
        e.setEmail(dto.getEmail());
        e.setTelefone(dto.getTelefone());
        e.setAtiva(dto.getAtiva());
    }
}
