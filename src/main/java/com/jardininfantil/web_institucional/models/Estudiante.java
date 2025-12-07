package com.jardininfantil.web_institucional.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    private Long estudianteId;
    private Long acudienteId;
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
