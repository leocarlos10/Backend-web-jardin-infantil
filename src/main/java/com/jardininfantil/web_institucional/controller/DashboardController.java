package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.dto.dashboard.DashboardStatsResponse;
import com.jardininfantil.web_institucional.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Response<DashboardStatsResponse>> getStats() {
        DashboardStatsResponse stats = dashboardService.getDashboardStats();

        Response<DashboardStatsResponse> response = Response.<DashboardStatsResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("SUCCESS")
                .data(stats)
                .build();

        return ResponseEntity.ok(response);
    }
}