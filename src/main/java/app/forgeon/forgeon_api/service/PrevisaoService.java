package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PrevisaoService {

    private final DashboardRepository repo;

    public PrevisaoService(DashboardRepository repo) {
        this.repo = repo;
    }

    public Double preverFaturamentoProximoMes(UUID empresaPublicId) {

        List<Map<String, Object>> historico = repo.faturamentoMensal(empresaPublicId);

        if (historico == null || historico.size() < 3) return null;

        double soma = 0;

        for (int i = historico.size() - 3; i < historico.size(); i++) {
            soma += ((Number) historico.get(i).get("faturamento")).doubleValue();
        }

        return Math.round((soma / 3) * 100.0) / 100.0;
    }
}
