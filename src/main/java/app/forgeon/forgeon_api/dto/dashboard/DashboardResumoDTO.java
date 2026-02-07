package app.forgeon.forgeon_api.dto.dashboard;

import app.forgeon.forgeon_api.dto.alerta.AlertaDTO;

import java.util.List;
import java.util.Map;

public record DashboardResumoDTO(

        Long produtosTotais,
        Long clientesTotais,
        Long impressorasAtivas,
        Long producoesAtivas,

        Double faturamentoMesAtual,
        Long vendasMesAtual,
        Double ticketMedioMesAtual,

        List<Map<String, Object>> faturamentoMensal,
        List<Map<String, Object>> vendasPorMes,
        List<Map<String, Object>> ticketMedioMensal,

        List<Map<String, Object>> topProdutosPorFaturamento,

        Double eficienciaMedia,
        Double percentualPerdaFilamento,

        List<AlertaDTO> alertas,
        Double previsaoFaturamento

) {}
