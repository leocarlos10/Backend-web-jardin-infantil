package com.jardininfantil.web_institucional.dto.matricula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaRequest {

    @NotNull(message = "El ID del estudiante es requerido")
    private Long estudianteId;

    @NotNull(message = "La fecha es requerida")
    private LocalDate fecha;

    @NotBlank(message = "El grado es requerido")
    private String grado;

    private BigDecimal valorTotal;

    private BigDecimal contratoFirmado;

    @NotBlank(message = "El estado de matrícula es requerido")
    private String estadoMatricula;
}
