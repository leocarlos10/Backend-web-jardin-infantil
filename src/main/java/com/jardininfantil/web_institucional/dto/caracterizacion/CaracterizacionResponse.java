package com.jardininfantil.web_institucional.dto.caracterizacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaracterizacionResponse {
    private Long idCaracterizacion;
    private Long estudianteId;
    private boolean beneficiario_renta_ciudadana;
    private int numero_miembro_familia;
    private String estrato_sisben;
    private String nivel_sisben;
    private String EPS;
    private String regimen_contributivo;
    private String regimen_subsidiado;
    private boolean pertenece_comunidad_indigena;
    private String nombre_co_indigena;
    private boolean desplazado;
    private String municipio_expulsor;
    private boolean discapacidad;
    private String tipo_discapacidad;
    private boolean hijo_unico;
    private String Enfermedades;
    private boolean viene_colegio_privado;
    private String nombre_colegio;
    private boolean viene_hogar_ICBF;
    private String nombre_hogar_ICBF;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
