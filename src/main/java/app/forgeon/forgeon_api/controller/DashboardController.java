package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.dashboard.DashboardResumoDTO;
import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * 📊 DASHBOARD COMPLETO
     * - KPIs
     * - Gráficos
     * - Alertas
     * - Previsão de faturamento
     */
    @GetMapping
    public ResponseEntity<DashboardResumoDTO> getResumo() {

        AuthContext auth = AuthContextHolder.get();

        return ResponseEntity.ok(
                dashboardService.getResumo(
                        auth.getEmpresaPublicId()
                )
        );
    }
}
