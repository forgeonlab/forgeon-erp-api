package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.cliente.ClienteCreateDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteDTO;
import app.forgeon.forgeon_api.dto.cliente.ClienteEstatisticasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {

    Page<ClienteDTO> listar(
            Boolean ativo,
            String search,
            Pageable pageable,
            UUID empresaPublicId
    );

    ClienteDTO buscarPorId(
            UUID id,
            UUID empresaPublicId
    );

    ClienteDTO criar(
            ClienteCreateDTO dto,
            UUID empresaPublicId,
            UUID usuarioPublicId
    );

    ClienteDTO atualizar(
            UUID id,
            ClienteCreateDTO dto,
            UUID empresaPublicId
    );

    void alterarStatus(
            UUID id,
            Boolean ativo,
            UUID empresaPublicId
    );

    ClienteEstatisticasDTO estatisticas(
            UUID empresaPublicId
    );
}
