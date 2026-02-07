package app.forgeon.forgeon_api.service;

import app.forgeon.forgeon_api.dto.alerta.AlertaDTO;
import app.forgeon.forgeon_api.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AlertaService {

    private final DashboardRepository repo;

    public AlertaService(DashboardRepository repo) {
        this.repo = repo;
    }

    public List<AlertaDTO> gerarAlertas(UUID empresaPublicId) {

        List<AlertaDTO> alertas = new ArrayList<>();

        Double eficiencia = repo.eficienciaMedia(empresaPublicId);
        if (eficiencia != null && eficiencia < 80) {
            alertas.add(new AlertaDTO(
                    "EFICIENCIA_BAIXA",
                    "CRITICAL",
                    "Eficiência de produção abaixo de 80%",
                    eficiencia,
                    80.0
            ));
        }

        Double perda = repo.percentualPerdaFilamento(empresaPublicId);
        if (perda != null && perda > 10) {
            alertas.add(new AlertaDTO(
                    "PERDA_FILAMENTO",
                    "WARNING",
                    "Perda de filamento acima de 10%",
                    perda,
                    10.0
            ));
        }

        Map<String, Object> comp = repo.comparativoMensal(empresaPublicId);
        if (comp != null && comp.get("mes_anterior") != null) {
            double atual = ((Number) comp.get("mes_atual")).doubleValue();
            double anterior = ((Number) comp.get("mes_anterior")).doubleValue();

            if (anterior > 0 && atual < anterior * 0.75) {
                alertas.add(new AlertaDTO(
                        "FATURAMENTO_QUEDA",
                        "WARNING",
                        "Faturamento caiu mais de 25% em relação ao mês anterior",
                        atual,
                        anterior
                ));
            }
        }

        return alertas;
    }
}
