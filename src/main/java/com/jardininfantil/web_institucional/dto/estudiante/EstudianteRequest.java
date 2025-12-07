package com.jardininfantil.web_institucional.dto.estudiante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteRequest {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotNull(message = "El segund nombre es requerido")
    private String segundoNombre;

    @NotBlank(message = "El primer apellido es requerido")
    private String primerApellido;

    @NotBlank(message = "El segundo apellido es requerido")
    private String segundoApellido;

    @NotBlank(message = "Numero de registro civil es obligatorio")
    private String numeroRegistroCivil;

    @NotNull(message = "Fecha de expedicion es obligatoria")
    private LocalDate fechaExp;
    
    
    @NotNull(message = "La fecha de nacimiento es requerida")
    private LocalDate fechaNacimiento;
    
    @NotNull(message = "Edad es requerida")
    private Integer edad;

    @NotBlank(message = "El sexo es requerido")
    private String sexo;
    
    @NotNull(message = "Tipo de sangre es requerido")
    private String tipoSangre;
   
    @NotNull(message = "Lugar de naciemiento requerido")
    private String lugarNacimiento;
    
    @NotNull(message = "Municipio es requerido")
    private String municipio;
    
    @NotNull(message = "Departamento es  requerido")
    private String departamento; 
    
    @NotNull(message = "Direccion es requerida") 
    private String direccion;
    
    @NotNull(message = "Barrio es requerida") 
    private String barrio;
    
    @NotNull(message = "Correo de un padre es requerido") 
    private String correoPadres;

    @NotNull(message = "Tipo de estudiante es requerido") 
    private String tipoEstudiante;
}
