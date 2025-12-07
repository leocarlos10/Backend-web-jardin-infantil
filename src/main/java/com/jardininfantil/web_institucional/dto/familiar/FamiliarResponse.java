

package com.jardininfantil.web_institucional.dto.familiar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamiliarResponse {

    private Long estudiante_id;
    private String nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String cedula;
    private LocalDate fecha_expedicion_documento;
    private LocalDate fecha_nacimiento;
    private String municipio;
    private String direccion;
    private String barrio;
    private String telefono;
    private String correo;
    private String nivel_educativo;
    private String ocupacion;
    private String tipo_familiar;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
 