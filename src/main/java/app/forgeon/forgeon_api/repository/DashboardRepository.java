package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.model.Dashboard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, UUID> {

    @Query("""
        select count(p)
        from Produto p
        where p.empresaPublicId = :empresaPublicId
    """)
    Long countProdutos(@Param("empresaPublicId") UUID empresaPublicId);

    @Query("""
        select count(c)
        from Cliente c
        where c.empresa.publicId = :empresaPublicId
    """)
    Long countClientes(@Param("empresaPublicId") UUID empresaPublicId);

    @Query("""
        select count(i)
        from Impressora i
        where i.empresaPublicId = :empresaPublicId
          and i.ativo = true
    """)
    Long countImpressorasAtivas(@Param("empresaPublicId") UUID empresaPublicId);

    @Query("""
        select count(p)
        from Producao p
        where p.empresaPublicId = :empresaPublicId
          and p.status = :status
    """)
    Long countProducoesAtivas(
            @Param("empresaPublicId") UUID empresaPublicId,
            @Param("status") StatusProducao status
    );

    @Query("""
        select v.criadoEm, v.total
        from Venda v
        where v.empresaPublicId = :empresaPublicId
          and v.status = :status
          and v.ativo = true
        order by v.criadoEm asc
    """)
    List<Object[]> listarVendasConcluidas(@Param("empresaPublicId") UUID empresaPublicId,
                                          @Param("status") StatusVenda status);

    @Query("""
        select v.produtoNome, sum(v.total)
        from Venda v
        where v.empresaPublicId = :empresaPublicId
          and v.status = :status
          and v.ativo = true
        group by v.produtoPublicId, v.produtoNome
        order by sum(v.total) desc
    """)
    List<Object[]> topProdutosPorFaturamento(@Param("empresaPublicId") UUID empresaPublicId,
                                             @Param("status") StatusVenda status,
                                             Pageable pageable);

    @Query("""
        select coalesce(sum(p.quantidadeBoa), 0), coalesce(sum(p.quantidadePlanejada), 0)
        from Producao p
        where p.empresaPublicId = :empresaPublicId
          and p.status = :status
    """)
    Object[] resumoEficiencia(@Param("empresaPublicId") UUID empresaPublicId,
                              @Param("status") StatusProducao status);

    @Query("""
        select coalesce(sum(pe.pesoPerdido), 0)
        from Perda pe
        where pe.producao.empresaPublicId = :empresaPublicId
    """)
    Double totalPesoPerdido(@Param("empresaPublicId") UUID empresaPublicId);

    @Query("""
        select coalesce(sum(cf.pesoUsado), 0)
        from ConsumoFilamento cf
        where cf.producao.empresaPublicId = :empresaPublicId
    """)
    Double totalPesoUsado(@Param("empresaPublicId") UUID empresaPublicId);

    default List<Map<String, Object>> faturamentoMensal(UUID empresaPublicId) {
        Map<YearMonth, Double> serie = new LinkedHashMap<>();

        for (Object[] linha : listarVendasConcluidas(empresaPublicId, StatusVenda.CONCLUIDA)) {
            LocalDateTime criadoEm = (LocalDateTime) linha[0];
            YearMonth mes = YearMonth.from(criadoEm);
            double total = toDouble(linha[1]);
            serie.merge(mes, total, Double::sum);
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<YearMonth, Double> entry : serie.entrySet()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", entry.getKey().toString());
            item.put("faturamento", round(entry.getValue(), 2));
            resultado.add(item);
        }
        return resultado;
    }

    default List<Map<String, Object>> vendasPorMes(UUID empresaPublicId) {
        Map<YearMonth, Long> serie = new LinkedHashMap<>();

        for (Object[] linha : listarVendasConcluidas(empresaPublicId, StatusVenda.CONCLUIDA)) {
            LocalDateTime criadoEm = (LocalDateTime) linha[0];
            YearMonth mes = YearMonth.from(criadoEm);
            serie.merge(mes, 1L, Long::sum);
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<YearMonth, Long> entry : serie.entrySet()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", entry.getKey().toString());
            item.put("total", entry.getValue());
            resultado.add(item);
        }
        return resultado;
    }

    default List<Map<String, Object>> ticketMedioMensal(UUID empresaPublicId) {
        Map<YearMonth, Double> faturamento = new LinkedHashMap<>();
        Map<YearMonth, Long> vendas = new LinkedHashMap<>();

        for (Object[] linha : listarVendasConcluidas(empresaPublicId, StatusVenda.CONCLUIDA)) {
            LocalDateTime criadoEm = (LocalDateTime) linha[0];
            YearMonth mes = YearMonth.from(criadoEm);
            double total = toDouble(linha[1]);

            faturamento.merge(mes, total, Double::sum);
            vendas.merge(mes, 1L, Long::sum);
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<YearMonth, Double> entry : faturamento.entrySet()) {
            long quantidade = vendas.getOrDefault(entry.getKey(), 0L);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", entry.getKey().toString());
            item.put("ticket_medio", quantidade > 0 ? round(entry.getValue() / quantidade, 2) : 0.0);
            resultado.add(item);
        }
        return resultado;
    }

    default Map<String, Object> vendasMesAtual(UUID empresaPublicId) {
        YearMonth mesAtual = YearMonth.now();
        long totalVendas = 0L;
        double valorTotal = 0.0;

        for (Object[] linha : listarVendasConcluidas(empresaPublicId, StatusVenda.CONCLUIDA)) {
            LocalDateTime criadoEm = (LocalDateTime) linha[0];
            if (YearMonth.from(criadoEm).equals(mesAtual)) {
                totalVendas++;
                valorTotal += toDouble(linha[1]);
            }
        }

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("totalVendas", totalVendas);
        resultado.put("valorTotal", round(valorTotal, 2));
        return resultado;
    }

    default Map<String, Object> comparativoMensal(UUID empresaPublicId) {
        Map<YearMonth, Double> faturamento = new LinkedHashMap<>();
        for (Object[] linha : listarVendasConcluidas(empresaPublicId, StatusVenda.CONCLUIDA)) {
            LocalDateTime criadoEm = (LocalDateTime) linha[0];
            YearMonth mes = YearMonth.from(criadoEm);
            faturamento.merge(mes, toDouble(linha[1]), Double::sum);
        }

        YearMonth mesAtual = YearMonth.now();
        YearMonth mesAnterior = mesAtual.minusMonths(1);

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("mes_atual", round(faturamento.getOrDefault(mesAtual, 0.0), 2));
        resultado.put("mes_anterior", round(faturamento.getOrDefault(mesAnterior, 0.0), 2));
        return resultado;
    }

    default Double eficienciaMedia(UUID empresaPublicId) {
        Object[] resumo = resumoEficiencia(empresaPublicId, StatusProducao.FINALIZADA);
        double quantidadeBoa = toDouble(resumo[0]);
        double quantidadePlanejada = toDouble(resumo[1]);
        if (quantidadePlanejada <= 0) {
            return 0.0;
        }
        return round((quantidadeBoa / quantidadePlanejada) * 100.0, 1);
    }

    default Double percentualPerdaFilamento(UUID empresaPublicId) {
        double pesoPerdido = toDouble(totalPesoPerdido(empresaPublicId));
        double pesoUsado = toDouble(totalPesoUsado(empresaPublicId));
        double base = pesoPerdido + pesoUsado;
        if (base <= 0) {
            return 0.0;
        }
        return round((pesoPerdido / base) * 100.0, 2);
    }

    private static double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal.doubleValue();
        }
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return Double.parseDouble(String.valueOf(value));
    }

    private static double round(double value, int scale) {
        return BigDecimal.valueOf(value)
                .setScale(scale, java.math.RoundingMode.HALF_UP)
                .doubleValue();
    }
}
