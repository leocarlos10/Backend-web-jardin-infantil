package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.familiar.FamiliarRequest;
import com.jardininfantil.web_institucional.dto.familiar.FamiliarResponse;
import com.jardininfantil.web_institucional.exception.DataExistException;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.Familiar;
import com.jardininfantil.web_institucional.repository.FamiliarRepository;
import org.springframework.stereotype.Service;

@Service
public class FamiliarService {

    private final FamiliarRepository familiarRepository;

    public FamiliarService(FamiliarRepository familiarRepository) {
        this.familiarRepository = familiarRepository;
    }

    public FamiliarResponse crearFamiliar(FamiliarRequest request) {
        Familiar familiar = new Familiar();
        familiar.setEstudiante_id(request.getEstudiante_id());
        familiar.setNombre(request.getNombre());
        familiar.setSegundo_nombre(request.getSegundo_nombre());
        familiar.setPrimer_apellido(request.getPrimer_apellido());
        familiar.setSegundo_apellido(request.getSegundo_apellido());
        familiar.setCedula(request.getCedula());
        familiar.setFecha_expedicion_documento(
            request.getFecha_expedicion_documento()
        );
        familiar.setFecha_nacimiento(request.getFecha_nacimiento());
        familiar.setMunicipio(request.getMunicipio());
        familiar.setDireccion(request.getDireccion());
        familiar.setBarrio(request.getBarrio());
        familiar.setTelefono(request.getTelefono());
        familiar.setCorreo(request.getCorreo());
        familiar.setNivel_educativo(request.getNivel_educativo());
        familiar.setOcupacion(request.getOcupacion());
        familiar.setTipo_familiar(request.getTipo_familiar());

        Familiar familiarSaved = familiarRepository.save(familiar);

        return FamiliarResponse.builder()
            .estudiante_id(familiarSaved.getEstudiante_id())
            .nombre(familiarSaved.getNombre())
            .segundo_nombre(familiarSaved.getSegundo_nombre())
            .primer_apellido(familiarSaved.getPrimer_apellido())
            .segundo_apellido(familiarSaved.getSegundo_apellido())
            .cedula(familiarSaved.getCedula())
            .fecha_expedicion_documento(
                familiarSaved.getFecha_expedicion_documento()
            )
            .fecha_nacimiento(familiarSaved.getFecha_nacimiento())
            .municipio(familiarSaved.getMunicipio())
            .direccion(familiarSaved.getDireccion())
            .barrio(familiarSaved.getBarrio())
            .telefono(familiarSaved.getTelefono())
            .correo(familiarSaved.getCorreo())
            .nivel_educativo(familiarSaved.getNivel_educativo())
            .ocupacion(familiarSaved.getOcupacion())
            .tipo_familiar(familiarSaved.getTipo_familiar())
            //.createdAt(familiarSaved.getCreatedAt())
            //.updatedAt(familiarSaved.getUpdatedAt())
            .build();
    }
}
