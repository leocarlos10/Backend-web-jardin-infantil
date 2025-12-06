package com.jardininfantil.web_institucional.models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acudiente {
    private Long acudienteId;
    private Long usuarioId;
    private String nombre;
    private String segundoNombre; 
    private String apellido;
    private String segundoApellido; 
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDate fechaExpedicion;
    private LocalDate fechaNacimiento;
    private String municipio; 
    private String direccion;
    private String barrio; 
    private String telefono;
    private String correo;
    private String nivelEducativo;
    private String ocupacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
