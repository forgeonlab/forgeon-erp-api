package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    // 🔍 Exemplo de consulta personalizada:
    Empresa findByCnpj(String cnpj);

    // Ou para buscar apenas empresas ativas:
    java.util.List<Empresa> findByAtivaTrue();
}