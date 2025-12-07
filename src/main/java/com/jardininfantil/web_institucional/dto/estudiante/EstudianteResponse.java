package com.jardininfantil.web_institucional.dto.estudiante;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteResponse {
    private Long estudianteId;
    private Long acudienteId;
    private String nombreAcudiente;
    private String nombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String numeroRegistroCivil;
    private LocalDate fechaExp;
    private LocalDate fechaNacimiento;
    private String tipoSangre;
    private String sexo;
    private String correoPadres;
    private Integer edad;
    private String lugarNacimiento;
    private String municipio;
    private String departamento;
    private String direccion;
    private String barrio;
    private String tipoEstudiante;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
