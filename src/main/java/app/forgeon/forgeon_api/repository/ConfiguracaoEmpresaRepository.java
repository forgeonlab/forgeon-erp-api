package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.ConfiguracaoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfiguracaoEmpresaRepository extends JpaRepository<ConfiguracaoEmpresa, Long> {

    List<ConfiguracaoEmpresa> findByEmpresaId(Long empresaId);

    Optional<ConfiguracaoEmpresa> findByEmpresaIdAndChave(Long empresaId, String chave);

    boolean existsByEmpresaIdAndChave(Long empresaId, String chave);
}
