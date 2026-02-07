package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByIdAndEmpresaId(
            UUID id,
            UUID empresaId
    );

    @Query("""
        select c
        from Cliente c
        where c.empresa.id = :empresaId
          and (:ativo is null or c.ativo = :ativo)
          and (
                :search is null
                or lower(c.nome) like lower(concat('%', :search, '%'))
                or lower(c.email) like lower(concat('%', :search, '%'))
              )
        order by c.id desc
    """)
    Page<Cliente> buscar(
            UUID empresaId,
            Boolean ativo,
            String search,
            Pageable pageable
    );

    long countByEmpresaId(UUID empresaId);

    long countByEmpresaIdAndAtivoTrue(UUID empresaId);

    long countByEmpresaIdAndAtivoFalse(UUID empresaId);
}
