package com.jardininfantil.web_institucional.dto.familiar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamiliarRequest {

    @NotNull(message = "obligatorio")
    private Long estudiante_id;

    @NotBlank(message = "obligatorio")
    private String nombre;

    @NotBlank(message = "obligatorio")
    private String segundo_nombre;

    @NotBlank(message = "obligatorio")
    private String primer_apellido;

    @NotBlank(message = "obligatorio")
    private String segundo_apellido;

    @NotBlank(message = "obligatorio")
    private String cedula;

    @NotNull(message = "obligatorio")
    private LocalDate fecha_expedicion_documento;

    @NotNull(message = "obligatorio")
    private LocalDate fecha_nacimiento;

    @NotBlank(message = "obligatorio")
    private String municipio;

    @NotBlank(message = "obligatorio")
    private String direccion;

    @NotBlank(message = "obligatorio")
    private String barrio;

    @NotBlank(message = "obligatorio")
    private String telefono;

    @NotBlank(message = "obligatorio")
    private String correo;

    @NotBlank(message = "obligatorio")
    private String nivel_educativo;

    @NotBlank(message = "obligatorio")
    private String ocupacion;

    @NotBlank(message = "obligatorio")
    private String tipo_familiar;
}
