package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, UUID> {

    /* === DADOS GERAIS ======================================================= */

    @Query(value = """
        SELECT COUNT(*) FROM produtos WHERE empresa_id = :empresaId
    """, nativeQuery = true)
    Long countProdutos(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COUNT(*) FROM clientes WHERE empresa_id = :empresaId
    """, nativeQuery = true)
    Long countClientes(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COUNT(*) FROM impressoras 
        WHERE empresa_id = :empresaId AND ativo = 1
    """, nativeQuery = true)
    Long countImpressorasAtivas(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COUNT(*) FROM producoes 
        WHERE empresa_id = :empresaId AND status = 'EM_ANDAMENTO'
    """, nativeQuery = true)
    Long countProducoesAtivas(@Param("empresaId") UUID empresaId);

    @Query(value = """
  SELECT DATE_FORMAT(MIN(data), '%b') AS mes,
      SUM(valor_total) AS total
  FROM vendas
  WHERE empresa_id = :empresaId
    AND status = 'CONCLUIDA'
    AND YEAR(data) = YEAR(CURDATE())
  GROUP BY YEAR(data), MONTH(data)
  ORDER BY YEAR(data), MONTH(data)
""", nativeQuery = true)
    List<Map<String, Object>> faturamentoMensal(@Param("empresaId") UUID empresaId);




    @Query(value = """
        SELECT COALESCE(AVG(preco_unitario), 0)
        FROM vendas
        WHERE empresa_id = :empresaId AND status = 'CONCLUIDA'
    """, nativeQuery = true)
    Double ticketMedio(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COUNT(*) FROM vendas
        WHERE empresa_id = :empresaId AND status = 'CONCLUIDA'
    """, nativeQuery = true)
    Long totalVendasConcluidas(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COALESCE(SUM(peso_usado), 0)
        FROM consumo_filamento cf
        JOIN producoes p ON p.id = cf.producao_id
        WHERE p.empresa_id = :empresaId
    """, nativeQuery = true)
    Double consumoTotalFilamento(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COALESCE(SUM(peso_perdido), 0)
        FROM perdas pe
        JOIN producoes p ON p.id = pe.producao_id
        WHERE p.empresa_id = :empresaId
    """, nativeQuery = true)
    Double pesoPerdido(@Param("empresaId") UUID empresaId);

    /* === GRÁFICOS ========================================================== */

    @Query(value = """
        SELECT DATE(data) as dia, COUNT(*) as total
        FROM producoes
        WHERE empresa_id = :empresaId
          AND DATE(data) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
        GROUP BY DATE(data)
        ORDER BY dia ASC
    """, nativeQuery = true)
    List<Map<String, Object>> producaoSemanal(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT p.nome AS produto, SUM(v.quantidade) AS total
        FROM vendas v
        JOIN produtos p ON p.id = v.produto_id
        WHERE v.empresa_id = :empresaId
          AND v.status = 'CONCLUIDA'
        GROUP BY p.id
        ORDER BY total DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Map<String, Object>> topProdutosVendidos(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT material, SUM(peso_atual) as peso_restante
        FROM filamentos
        WHERE empresa_id = :empresaId AND ativo = 1
        GROUP BY material
    """, nativeQuery = true)
    List<Map<String, Object>> estoqueFilamentos(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT status, COUNT(*) as total
        FROM impressoras
        WHERE empresa_id = :empresaId
        GROUP BY status
    """, nativeQuery = true)
    List<Map<String, Object>> statusImpressoras(@Param("empresaId") UUID empresaId);

    /* === EFICIÊNCIA E INDICADORES ========================================= */

    @Query(value = """
        SELECT 
          ROUND( (SUM(quantidade_boa) / SUM(quantidade_planejada)) * 100 , 1)
        FROM producoes
        WHERE empresa_id = :empresaId
          AND status = 'FINALIZADA'
          AND quantidade_planejada > 0
    """, nativeQuery = true)
    Double eficienciaMedia(@Param("empresaId") UUID empresaId);

    @Query(value = """
        SELECT COUNT(*) FROM manutencoes m
        JOIN impressoras i ON i.id = m.impressora_id
        WHERE i.empresa_id = :empresaId
          AND DATE(m.data) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
    """, nativeQuery = true)
    Long manutencoesRecentes(@Param("empresaId") UUID empresaId);

    /* === OUTROS ============================================================ */

    @Query(value = """
        SELECT i.nome, i.status, p.id AS producao_id, p.quantidade_planejada, p.quantidade_boa
        FROM impressoras i
        LEFT JOIN producoes p 
          ON p.impressora_id = i.id
          AND p.status = 'EM_ANDAMENTO'
        WHERE i.empresa_id = :empresaId
          AND i.ativo = 1
    """, nativeQuery = true)
    List<Map<String, Object>> impressorasAtivas(@Param("empresaId") UUID empresaId);

}
