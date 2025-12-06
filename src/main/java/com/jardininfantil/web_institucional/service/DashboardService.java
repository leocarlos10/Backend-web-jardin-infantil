package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.dashboard.DashboardStatsResponse;
import com.jardininfantil.web_institucional.repository.DashboardRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardStatsResponse getDashboardStats() {
        return dashboardRepository.getDashboardStats();
    }
}
