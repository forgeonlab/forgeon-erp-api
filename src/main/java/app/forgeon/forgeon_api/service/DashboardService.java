package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.dashboard.DashboardResumoDTO;
import app.forgeon.forgeon_api.repository.DashboardRepository;
import org.springframework.stereotype.Service;

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

        Map<String, Object> vendasMes = repo.vendasMesAtual(empresaPublicId);

        Double faturamentoMesAtual = vendasMes != null
                ? ((Number) vendasMes.get("valorTotal")).doubleValue()
                : 0.0;

        Long vendasMesAtual = vendasMes != null
                ? ((Number) vendasMes.get("totalVendas")).longValue()
                : 0L;

        Double ticketMedioMesAtual =
                vendasMesAtual > 0 ? faturamentoMesAtual / vendasMesAtual : 0.0;

        return new DashboardResumoDTO(
                repo.countProdutos(empresaPublicId),
                repo.countClientes(empresaPublicId),
                repo.countImpressorasAtivas(empresaPublicId),
                repo.countProducoesAtivas(empresaPublicId),

                faturamentoMesAtual,
                vendasMesAtual,
                ticketMedioMesAtual,

                repo.faturamentoMensal(empresaPublicId),
                repo.vendasPorMes(empresaPublicId),
                repo.ticketMedioMensal(empresaPublicId),

                repo.topProdutosPorFaturamento(empresaPublicId),

                repo.eficienciaMedia(empresaPublicId),
                repo.percentualPerdaFilamento(empresaPublicId),

                alertaService.gerarAlertas(empresaPublicId),
                previsaoService.preverFaturamentoProximoMes(empresaPublicId)
        );
    }
}
