package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.empresa.ConfiguracaoEmpresaCreateDTO;
import app.forgeon.forgeon_api.dto.empresa.ConfiguracaoEmpresaDTO;
import app.forgeon.forgeon_api.model.ConfiguracaoEmpresa;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.repository.ConfiguracaoEmpresaRepository;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConfiguracaoEmpresaService {

    @Autowired
    private ConfiguracaoEmpresaRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<ConfiguracaoEmpresaDTO> listarPorEmpresa(UUID empresaId) {
        return repository.findByEmpresaId(empresaId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ConfiguracaoEmpresaDTO buscarPorChave(UUID empresaId, String chave) {
        return repository.findByEmpresaIdAndChave(empresaId, chave)
                .map(this::toDTO)
                .orElse(null);
    }

    public ConfiguracaoEmpresaDTO criar(ConfiguracaoEmpresaCreateDTO dto) {
        if (repository.existsByEmpresaIdAndChave(dto.getEmpresaId(), dto.getChave())) {
            throw new RuntimeException("Configuração já existe para esta empresa e chave!");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        ConfiguracaoEmpresa config = new ConfiguracaoEmpresa();
        config.setEmpresa(empresa);
        config.setChave(dto.getChave());
        config.setValor(dto.getValor());

        return toDTO(repository.save(config));
    }

    public ConfiguracaoEmpresaDTO atualizar(UUID id, ConfiguracaoEmpresaCreateDTO dto) {
        ConfiguracaoEmpresa config = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuração não encontrada"));

        config.setValor(dto.getValor());
        return toDTO(repository.save(config));
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    // 🔁 conversor entidade → DTO
    private ConfiguracaoEmpresaDTO toDTO(ConfiguracaoEmpresa config) {
        ConfiguracaoEmpresaDTO dto = new ConfiguracaoEmpresaDTO();
        dto.setId(config.getId());
        dto.setEmpresaId(config.getEmpresa().getId());
        dto.setChave(config.getChave());
        dto.setValor(config.getValor());
        return dto;
    }
}
