package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.enums.PapelUsuario;
import app.forgeon.forgeon_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // 🔍 Buscar por e-mail (para login)
    Optional<Usuario> findByEmail(String email);

    // 🔍 Listar usuários de uma empresa específica
    List<Usuario> findByEmpresaId(Long empresaId);

    // 🔍 Filtrar por papel
    List<Usuario> findByPapel(PapelUsuario papel);

    // 🔍 Apenas usuários ativos
    List<Usuario> findByAtivoTrue();

    // 🔍 Buscar ativo + e-mail (útil pro login seguro)
    Optional<Usuario> findByEmailAndAtivoTrue(String email);
}
