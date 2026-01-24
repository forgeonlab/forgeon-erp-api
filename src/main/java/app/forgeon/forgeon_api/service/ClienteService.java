package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.cliente.ClienteCreateDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteDTO;
import app.forgeon.forgeon_api.model.Cliente;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.repository.ClienteRepository;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<ClienteDTO> listarPorEmpresa(Long empresaId) {
        return clienteRepository.findByEmpresaId(empresaId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return toDTO(cliente);
    }

    public ClienteDTO criar(ClienteCreateDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Cliente cliente = new Cliente();
        cliente.setEmpresa(empresa);
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setAtivo(true);

        return toDTO(clienteRepository.save(cliente));
    }

    public ClienteDTO atualizar(Long id, ClienteCreateDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());

        return toDTO(clienteRepository.save(cliente));
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    public void alterarStatus(Long id, Boolean ativo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(ativo);
        clienteRepository.save(cliente);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setEmpresaId(cliente.getEmpresa().getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setAtivo(cliente.getAtivo());
        return dto;
    }
}
