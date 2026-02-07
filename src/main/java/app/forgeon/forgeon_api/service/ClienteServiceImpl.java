package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.cliente.ClienteCreateDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteEstatisticasDTO;
import app.forgeon.forgeon_api.model.Cliente;
import app.forgeon.forgeon_api.model.Empresa;
import app.forgeon.forgeon_api.repository.ClienteRepository;
import app.forgeon.forgeon_api.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public ClienteServiceImpl(
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    /* =========================
       RESOLVER EMPRESA
    ========================= */
    private Empresa empresaPorPublicId(UUID empresaPublicId) {
        return empresaRepository.findByPublicId(empresaPublicId)
                .orElseThrow(() -> new IllegalStateException("Empresa não encontrada"));
    }

    /* =========================
       LISTAR
    ========================= */
    @Override
    public Page<ClienteDTO> listar(
            Boolean ativo,
            String search,
            Pageable pageable,
            UUID empresaPublicId
    ) {
        Empresa empresa = empresaPorPublicId(empresaPublicId);

        return clienteRepository.buscar(
                empresa.getId(), // ID INTERNO
                ativo,
                search,
                pageable
        ).map(ClienteDTO::fromEntity);
    }

    /* =========================
       BUSCAR
    ========================= */
    @Override
    public ClienteDTO buscarPorId(
            UUID id,
            UUID empresaPublicId
    ) {
        Empresa empresa = empresaPorPublicId(empresaPublicId);

        Cliente cliente = clienteRepository
                .findByIdAndEmpresaId(
                        id,
                        empresa.getId()
                )
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        return ClienteDTO.fromEntity(cliente);
    }

    /* =========================
       CRIAR
    ========================= */
    @Override
    public ClienteDTO criar(
            ClienteCreateDTO dto,
            UUID empresaPublicId,
            UUID usuarioPublicId
    ) {
        Empresa empresa = empresaPorPublicId(empresaPublicId);

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setAtivo(true);
        cliente.setEmpresa(empresa);

        clienteRepository.save(cliente);

        return ClienteDTO.fromEntity(cliente);
    }

    /* =========================
       ATUALIZAR
    ========================= */
    @Override
    public ClienteDTO atualizar(
            UUID id,
            ClienteCreateDTO dto,
            UUID empresaPublicId
    ) {
        Empresa empresa = empresaPorPublicId(empresaPublicId);

        Cliente cliente = clienteRepository
                .findByIdAndEmpresaId(
                        id,
                        empresa.getId()
                )
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());

        clienteRepository.save(cliente);

        return ClienteDTO.fromEntity(cliente);
    }

    /* =========================
       STATUS
    ========================= */
    @Override
    public void alterarStatus(
            UUID id,
            Boolean ativo,
            UUID empresaPublicId
    ) {
        Empresa empresa = empresaPorPublicId(empresaPublicId);

        Cliente cliente = clienteRepository
                .findByIdAndEmpresaId(
                        id,
                        empresa.getId()
                )
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        cliente.setAtivo(ativo);
        clienteRepository.save(cliente);
    }

    /* =========================
       ESTATÍSTICAS
    ========================= */
    @Override
    public ClienteEstatisticasDTO estatisticas(UUID empresaPublicId) {
        Empresa empresa = empresaPorPublicId(empresaPublicId);

        long total = clienteRepository.countByEmpresaId(empresa.getId());
        long ativos = clienteRepository.countByEmpresaIdAndAtivoTrue(empresa.getId());
        long inativos = clienteRepository.countByEmpresaIdAndAtivoFalse(empresa.getId());

        return new ClienteEstatisticasDTO(
                total,
                ativos,
                inativos,
                0 // se quiser depois, adiciona createdAt
        );
    }
}
