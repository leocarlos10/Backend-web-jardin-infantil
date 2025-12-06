package com.jardininfantil.web_institucional.dto.acudiente;

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
public class AcudienteResponse {
    private Long acudienteId;
    private Long usuarioId;
    private String nombre;
    private String segundoNombre;
    private String apellido;
    private String segundoApellido;
    private String cedula;
    private LocalDate fechaExpedicion;
    private LocalDate fechaNacimiento;
    private String municipio;
    private String direccion;
    private String barrio;
    private String telefono;
    private String correo;
    private String nivelEducativo;
    private String ocupacion;
}