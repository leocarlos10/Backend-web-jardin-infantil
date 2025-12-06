package com.jardininfantil.web_institucional.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private Long totalMatriculas;
    private Long totalReservas;
    private Long totalEstudiantes;
    private Long totalEncuestas;
}