package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.dashboard.DashboardResumoDTO;
import app.forgeon.forgeon_api.enums.StatusProducao;
import app.forgeon.forgeon_api.enums.StatusVenda;
import app.forgeon.forgeon_api.repository.DashboardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DashboardService {

    private final DashboardRepository repo;
    private final AlertaService alertaService;
    private final PrevisaoService previsaoService;

    public DashboardService(
            DashboardRepository repo,
            AlertaService alertaService,
            PrevisaoService previsaoService
    ) {
        this.repo = repo;
        this.alertaService = alertaService;
        this.previsaoService = previsaoService;
    }

    public DashboardResumoDTO getResumo(UUID empresaPublicId) {
        List<Object[]> vendasConcluidas = repo.listarVendasConcluidas(empresaPublicId, StatusVenda.CONCLUIDA);
        YearMonth mesAtual = YearMonth.now();

        double faturamentoMesAtual = 0.0;
        long vendasMesAtual = 0L;

        Map<YearMonth, Double> faturamentoPorMes = new LinkedHashMap<>();
        Map<YearMonth, Long> vendasPorMes = new LinkedHashMap<>();

        for (Object[] linha : vendasConcluidas) {
            LocalDateTime criadoEm = (LocalDateTime) linha[0];
            double total = toDouble(linha[1]);
            YearMonth mes = YearMonth.from(criadoEm);

            faturamentoPorMes.merge(mes, total, Double::sum);
            vendasPorMes.merge(mes, 1L, Long::sum);

            if (mes.equals(mesAtual)) {
                faturamentoMesAtual += total;
                vendasMesAtual++;
            }
        }

        double ticketMedioMesAtual = vendasMesAtual > 0 ? faturamentoMesAtual / vendasMesAtual : 0.0;
        List<Map<String, Object>> faturamentoMensal = mapearSerieFaturamento(faturamentoPorMes);
        List<Map<String, Object>> vendasMensal = mapearSerieContagem(vendasPorMes);
        List<Map<String, Object>> ticketMedioMensal = mapearSerieTicket(faturamentoPorMes, vendasPorMes);
        List<Map<String, Object>> topProdutos = mapearTopProdutos(
                repo.topProdutosPorFaturamento(empresaPublicId, StatusVenda.CONCLUIDA, PageRequest.of(0, 5))
        );

        Object[] eficiencia = repo.resumoEficiencia(empresaPublicId, StatusProducao.FINALIZADA);
        double quantidadeBoa = toDouble(eficiencia[0]);
        double quantidadePlanejada = toDouble(eficiencia[1]);
        double eficienciaMedia = quantidadePlanejada > 0
                ? round((quantidadeBoa / quantidadePlanejada) * 100.0, 1)
                : 0.0;

        double pesoPerdido = defaultZero(repo.totalPesoPerdido(empresaPublicId));
        double pesoUsado = defaultZero(repo.totalPesoUsado(empresaPublicId));
        double percentualPerdaFilamento = (pesoUsado + pesoPerdido) > 0
                ? round((pesoPerdido / (pesoUsado + pesoPerdido)) * 100.0, 2)
                : 0.0;

        return new DashboardResumoDTO(
                repo.countProdutos(empresaPublicId),
                repo.countClientes(empresaPublicId),
                repo.countImpressorasAtivas(empresaPublicId),
                repo.countProducoesAtivas(empresaPublicId, StatusProducao.EM_PRODUCAO),
                faturamentoMesAtual,
                vendasMesAtual,
                ticketMedioMesAtual,
                faturamentoMensal,
                vendasMensal,
                ticketMedioMensal,
                topProdutos,
                eficienciaMedia,
                percentualPerdaFilamento,
                alertaService.gerarAlertas(empresaPublicId),
                previsaoService.preverFaturamentoProximoMes(empresaPublicId)
        );
    }

    private List<Map<String, Object>> mapearSerieFaturamento(Map<YearMonth, Double> faturamentoPorMes) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<YearMonth, Double> entry : faturamentoPorMes.entrySet()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", entry.getKey().toString());
            item.put("faturamento", round(entry.getValue(), 2));
            resultado.add(item);
        }
        return resultado;
    }

    private List<Map<String, Object>> mapearSerieContagem(Map<YearMonth, Long> vendasPorMes) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<YearMonth, Long> entry : vendasPorMes.entrySet()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", entry.getKey().toString());
            item.put("total", entry.getValue());
            resultado.add(item);
        }
        return resultado;
    }

    private List<Map<String, Object>> mapearSerieTicket(
            Map<YearMonth, Double> faturamentoPorMes,
            Map<YearMonth, Long> vendasPorMes
    ) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<YearMonth, Double> entry : faturamentoPorMes.entrySet()) {
            long quantidade = vendasPorMes.getOrDefault(entry.getKey(), 0L);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", entry.getKey().toString());
            item.put("ticket_medio", quantidade > 0 ? round(entry.getValue() / quantidade, 2) : 0.0);
            resultado.add(item);
        }
        return resultado;
    }

    private List<Map<String, Object>> mapearTopProdutos(List<Object[]> linhas) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] linha : linhas) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("produto", linha[0]);
            item.put("faturamento", round(toDouble(linha[1]), 2));
            resultado.add(item);
        }
        return resultado;
    }

    private double defaultZero(Double value) {
        return value == null ? 0.0 : value;
    }

    private double toDouble(Object value) {
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

    private double round(double value, int scale) {
        return BigDecimal.valueOf(value)
                .setScale(scale, java.math.RoundingMode.HALF_UP)
                .doubleValue();
    }
}
