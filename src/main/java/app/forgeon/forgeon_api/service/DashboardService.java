package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.log.LogRequest;
import app.forgeon.forgeon_api.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private LogService logService;

    public Map<String, Object> getResumo(Long empresaId, Long usuarioId) {
        Map<String, Object> data = new HashMap<>();

        // KPIs principais
        data.put("produtosTotais", dashboardRepository.countProdutos(empresaId));
        data.put("clientesTotais", dashboardRepository.countClientes(empresaId));
        data.put("impressorasAtivas", dashboardRepository.countImpressorasAtivas(empresaId));
        data.put("producoesAtivas", dashboardRepository.countProducoesAtivas(empresaId));
        data.put("faturamentoMensal", dashboardRepository.faturamentoMensal(empresaId));
        data.put("ticketMedio", dashboardRepository.ticketMedio(empresaId));
        data.put("vendasConcluidas", dashboardRepository.totalVendasConcluidas(empresaId));
        data.put("eficienciaMedia", dashboardRepository.eficienciaMedia(empresaId));
        data.put("manutencoesRecentes", dashboardRepository.manutencoesRecentes(empresaId));

        // Indicadores técnicos
        data.put("filamentoConsumidoKg", dashboardRepository.consumoTotalFilamento(empresaId));
        data.put("pesoPerdidoKg", dashboardRepository.pesoPerdido(empresaId));

        // Gráficos e listas
        data.put("graficoProducaoSemanal", dashboardRepository.producaoSemanal(empresaId));
        data.put("topProdutos", dashboardRepository.topProdutosVendidos(empresaId));
        data.put("filamentos", dashboardRepository.estoqueFilamentos(empresaId));
        data.put("impressoras", dashboardRepository.impressorasAtivas(empresaId));
        data.put("statusImpressoras", dashboardRepository.statusImpressoras(empresaId));

        // Log automático
        LogRequest log = new LogRequest();
        log.setEmpresaId(empresaId);
        log.setUsuarioId(usuarioId);
        log.setEntidade("Dashboard");
        log.setEntidadeId(null);
        log.setAcao("Dashboard consultado (resumo atualizado)");
        logService.registrar(log);

        return data;
    }
}
