package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.caracterizacion.CaracterizacionRequest;
import com.jardininfantil.web_institucional.dto.caracterizacion.CaracterizacionResponse;
import com.jardininfantil.web_institucional.models.CaracterizacionEstudiante;
import com.jardininfantil.web_institucional.repository.CaracterizacionRepository;
import org.springframework.stereotype.Service;

@Service
public class CaracterizacionService {

    private final CaracterizacionRepository caracterizacionRepository;

    public CaracterizacionService(
        CaracterizacionRepository caracterizacionRepository
    ) {
        this.caracterizacionRepository = caracterizacionRepository;
    }

    public CaracterizacionResponse crearCaracterizacion(
        CaracterizacionRequest request
    ) {
        CaracterizacionEstudiante caracterizacion =
            CaracterizacionEstudiante.builder()
                .estudiante_id(request.getEstudiante_id())
                .beneficiario_renta_ciudadana(
                    request.isBeneficiario_renta_ciudadana()
                )
                .numero_miembro_familia(request.getNumero_miembro_familia())
                .estrato_sisben(request.getEstrato_sisben())
                .nivel_sisben(request.getNivel_sisben())
                .EPS(request.getEPS())
                .regimen_contributivo(request.getRegimen_contributivo())
                .regimen_subsidiado(request.getRegimen_subsidiado())
                .pertenece_comunidad_indigena(
                    request.isPertenece_comunidad_indigena()
                )
                .nombre_co_indigena(
                    request.isPertenece_comunidad_indigena()
                        ? request.getNombre_co_indigena()
                        : null
                )
                .desplazado(request.isDesplazado())
                .municipio_expulsor(
                    request.isDesplazado()
                        ? request.getMunicipio_expulsor()
                        : null
                )
                .discapacidad(request.isDiscapacidad())
                .tipo_discapacidad(
                    request.isDiscapacidad()
                        ? request.getTipo_discapacidad()
                        : null
                )
                .hijo_unico(request.isHijo_unico())
                .Enfermedades(request.getEnfermedades())
                .viene_colegio_privado(request.isViene_colegio_privado())
                .nombre_colegio(
                    request.isViene_colegio_privado()
                        ? request.getNombre_colegio()
                        : null
                )
                .viene_hogar_ICBF(request.isViene_hogar_ICBF())
                .nombre_hogar_ICBF(
                    request.isViene_hogar_ICBF()
                        ? request.getNombre_hogar_ICBF()
                        : null
                )
                .build();

        CaracterizacionEstudiante caracterizacionSaved =
            caracterizacionRepository.save(caracterizacion);

        return CaracterizacionResponse.builder()
            .idCaracterizacion(caracterizacionSaved.getId_caracterizacion())
            .estudianteId(caracterizacionSaved.getEstudiante_id())
            .beneficiario_renta_ciudadana(
                caracterizacionSaved.isBeneficiario_renta_ciudadana()
            )
            .numero_miembro_familia(
                caracterizacionSaved.getNumero_miembro_familia()
            )
            .estrato_sisben(caracterizacionSaved.getEstrato_sisben())
            .nivel_sisben(caracterizacionSaved.getNivel_sisben())
            .EPS(caracterizacionSaved.getEPS())
            .regimen_contributivo(
                caracterizacionSaved.getRegimen_contributivo()
            )
            .regimen_subsidiado(caracterizacionSaved.getRegimen_subsidiado())
            .pertenece_comunidad_indigena(
                caracterizacionSaved.isPertenece_comunidad_indigena()
            )
            .nombre_co_indigena(caracterizacionSaved.getNombre_co_indigena())
            .desplazado(caracterizacionSaved.isDesplazado())
            .municipio_expulsor(caracterizacionSaved.getMunicipio_expulsor())
            .discapacidad(caracterizacionSaved.isDiscapacidad())
            .tipo_discapacidad(caracterizacionSaved.getTipo_discapacidad())
            .hijo_unico(caracterizacionSaved.isHijo_unico())
            .Enfermedades(caracterizacionSaved.getEnfermedades())
            .viene_colegio_privado(
                caracterizacionSaved.isViene_colegio_privado()
            )
            .nombre_colegio(caracterizacionSaved.getNombre_colegio())
            .viene_hogar_ICBF(caracterizacionSaved.isViene_hogar_ICBF())
            .nombre_hogar_ICBF(caracterizacionSaved.getNombre_hogar_ICBF())
            .build();
    }
}
