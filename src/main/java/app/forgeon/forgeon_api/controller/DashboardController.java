package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<Map<String, Object>> getResumo(
            @PathVariable Long empresaId,
            @RequestParam(required = false) Long usuarioId
    ) {
        return ResponseEntity.ok(dashboardService.getResumo(empresaId, usuarioId));
    }
}
