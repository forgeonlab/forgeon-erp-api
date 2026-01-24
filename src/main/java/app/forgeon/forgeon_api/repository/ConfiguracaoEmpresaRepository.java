package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.ConfiguracaoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfiguracaoEmpresaRepository extends JpaRepository<ConfiguracaoEmpresa, UUID> {

    List<ConfiguracaoEmpresa> findByEmpresaId(UUID empresaId);

    Optional<ConfiguracaoEmpresa> findByEmpresaIdAndChave(UUID empresaId, String chave);

    boolean existsByEmpresaIdAndChave(UUID empresaId, String chave);
}
