package com.jardininfantil.web_institucional.dto.acudiente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcudienteRequest {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    @NotBlank(message = "Segundo nombre es requerido")
    private String segundoNombre;

    @NotBlank(message = "El apellido es requerido")
    private String apellido;
    
    @NotBlank(message = "Segundo apellido es requerido")
    private String segundoApellido;
    
    @NotBlank(message = "El número de documento es requerido")
    private String cedula;

    @NotNull(message = "Fecha de espedicion es requerido")
    private LocalDate fechaExpedicion;
    
    @NotNull(message = "Fecha de nacimiento es requerido")
    private LocalDate fechaNacimiento;
    
    @NotBlank(message = "Municipio es requerido")
    private String municipio;
    
    @NotBlank(message = "Direccion es requerido")
    private String direccion;
    
    @NotBlank(message = "Barrio es requerido")
    private String barrio;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo es requerido")
    private String correo;

    @NotBlank(message = "Nivel Educativo es requerido")
    private String nivelEducativo;
    
    @NotBlank(message = "Ocupacion es requerido")
    private String ocupacion;
}
