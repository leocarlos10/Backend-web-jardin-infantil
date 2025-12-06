package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.dto.dashboard.DashboardStatsResponse;
import com.jardininfantil.web_institucional.repository.DashboardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DashboardStatsResponse getDashboardStats() {
        String sql = "CALL sp_get_dashboard_stats()";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> DashboardStatsResponse.builder()
                .totalMatriculas(rs.getLong("total_matriculas"))
                .totalReservas(rs.getLong("total_reservas"))
                .totalEstudiantes(rs.getLong("total_estudiantes"))
                .totalEncuestas(rs.getLong("total_encuestas"))
                .build());
    }
}
