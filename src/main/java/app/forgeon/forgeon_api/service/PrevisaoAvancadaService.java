package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.previsao.PrevisaoFaturamentoDTO;
import app.forgeon.forgeon_api.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrevisaoAvancadaService {

    private final DashboardRepository repo;

    public PrevisaoAvancadaService(DashboardRepository repo) {
        this.repo = repo;
    }

    public PrevisaoFaturamentoDTO prever(UUID empresaPublicId) {

        var historico = repo.faturamentoMensal(empresaPublicId);
        if (historico == null || historico.size() < 3) return null;

        int size = historico.size();

        double m1 = ((Number) historico.get(size - 3).get("faturamento")).doubleValue();
        double m2 = ((Number) historico.get(size - 2).get("faturamento")).doubleValue();
        double m3 = ((Number) historico.get(size - 1).get("faturamento")).doubleValue();

        double media = (m1 + m2 + m3) / 3;
        double tendencia = (m3 - m1) / 2;

        double previsao = media + tendencia;

        String status =
                tendencia > 0 ? "CRESCENDO" :
                        tendencia < 0 ? "CAINDO" :
                                "ESTAVEL";

        return new PrevisaoFaturamentoDTO(
                "Próximo mês",
                Math.round(previsao * 100.0) / 100.0,
                Math.round(tendencia * 100.0) / 100.0,
                status
        );
    }
}
