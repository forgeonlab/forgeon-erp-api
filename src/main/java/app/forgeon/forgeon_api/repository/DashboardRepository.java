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

    /* =======================
       CONTADORES GERAIS
    ======================= */

    @Query(value = """
        SELECT COUNT(*)
        FROM produtos
        WHERE empresa_public_id = :empresaPublicId
    """, nativeQuery = true)
    Long countProdutos(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT COUNT(*)
        FROM clientes c
        JOIN empresas e ON e.id = c.empresa_id
        WHERE e.public_id = :empresaPublicId
    """, nativeQuery = true)
    Long countClientes(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT COUNT(*)
        FROM impressoras i
        WHERE i.empresa_public_id = :empresaPublicId
          AND i.ativo = 1
    """, nativeQuery = true)
    Long countImpressorasAtivas(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT COUNT(*)
        FROM producoes p
        WHERE p.empresa_public_id = :empresaPublicId
          AND p.status = 'EM_PRODUCAO'
    """, nativeQuery = true)
    Long countProducoesAtivas(@Param("empresaPublicId") UUID empresaPublicId);

    /* =======================
       FINANCEIRO
    ======================= */

    @Query(value = """
        SELECT
          COUNT(*) AS totalVendas,
          COALESCE(SUM(v.total), 0) AS valorTotal
        FROM vendas v
        WHERE v.empresa_public_id = :empresaPublicId
          AND v.status = 'CONCLUIDA'
          AND YEAR(v.criado_em) = YEAR(CURDATE())
          AND MONTH(v.criado_em) = MONTH(CURDATE())
    """, nativeQuery = true)
    Map<String, Object> vendasMesAtual(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT
          DATE_FORMAT(v.criado_em, '%Y-%m') AS mes,
          SUM(v.total) AS faturamento
        FROM vendas v
        WHERE v.empresa_public_id = :empresaPublicId
          AND v.status = 'CONCLUIDA'
        GROUP BY DATE_FORMAT(v.criado_em, '%Y-%m')
        ORDER BY mes
    """, nativeQuery = true)
    List<Map<String, Object>> faturamentoMensal(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT
          DATE_FORMAT(v.criado_em, '%Y-%m') AS mes,
          COUNT(*) AS total
        FROM vendas v
        WHERE v.empresa_public_id = :empresaPublicId
          AND v.status = 'CONCLUIDA'
        GROUP BY DATE_FORMAT(v.criado_em, '%Y-%m')
        ORDER BY mes
    """, nativeQuery = true)
    List<Map<String, Object>> vendasPorMes(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT
          DATE_FORMAT(v.criado_em, '%Y-%m') AS mes,
          ROUND(SUM(v.total) / NULLIF(COUNT(*), 0), 2) AS ticket_medio
        FROM vendas v
        WHERE v.empresa_public_id = :empresaPublicId
          AND v.status = 'CONCLUIDA'
        GROUP BY DATE_FORMAT(v.criado_em, '%Y-%m')
        ORDER BY mes
    """, nativeQuery = true)
    List<Map<String, Object>> ticketMedioMensal(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT
          pr.nome AS produto,
          SUM(v.total) AS faturamento
        FROM vendas v
        JOIN produtos pr ON pr.public_id = v.produto_public_id
        WHERE v.empresa_public_id = :empresaPublicId
          AND v.status = 'CONCLUIDA'
        GROUP BY pr.id, pr.nome
        ORDER BY faturamento DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Map<String, Object>> topProdutosPorFaturamento(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT
          SUM(CASE
              WHEN YEAR(v.criado_em) = YEAR(CURDATE())
               AND MONTH(v.criado_em) = MONTH(CURDATE())
              THEN v.total ELSE 0 END) AS mes_atual,
          SUM(CASE
              WHEN YEAR(v.criado_em) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
               AND MONTH(v.criado_em) = MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
              THEN v.total ELSE 0 END) AS mes_anterior
        FROM vendas v
        WHERE v.empresa_public_id = :empresaPublicId
          AND v.status = 'CONCLUIDA'
    """, nativeQuery = true)
    Map<String, Object> comparativoMensal(@Param("empresaPublicId") UUID empresaPublicId);

    /* =======================
       PRODUÇÃO / MATERIAL
    ======================= */

    @Query(value = """
        SELECT ROUND(
            (SUM(p.quantidade_boa) / NULLIF(SUM(p.quantidade_planejada), 0)) * 100, 1
        )
        FROM producoes p
        WHERE p.empresa_public_id = :empresaPublicId
          AND p.status = 'FINALIZADA'
    """, nativeQuery = true)
    Double eficienciaMedia(@Param("empresaPublicId") UUID empresaPublicId);

    @Query(value = """
        SELECT
          ROUND(
            (SUM(pe.peso_perdido) /
            NULLIF(SUM(cf.peso_usado) + SUM(pe.peso_perdido), 0)) * 100,
            2
          )
        FROM perdas pe
        JOIN producoes p ON p.id = pe.producao_id
        JOIN consumo_filamento cf ON cf.producao_id = p.id
        WHERE p.empresa_public_id = :empresaPublicId
    """, nativeQuery = true)
    Double percentualPerdaFilamento(@Param("empresaPublicId") UUID empresaPublicId);
}
