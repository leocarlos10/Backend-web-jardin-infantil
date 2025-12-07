package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.familia.FamiliarRequest;
import com.jardininfantil.web_institucional.dto.familia.FamiliarResponse;
import com.jardininfantil.web_institucional.exception.DataExistException;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.Familiar;
import com.jardininfantil.web_institucional.repository.FamiliarRepository;
import org.springframework.stereotype.Service;

@Service
public class FamiliaService {

    private final FamiliarRepository familiarRepository;

    public FamiliaService(FamiliarRepository familiarRepository) {
        this.familiarRepository = familiarRepository;
    }

    public FamiliarResponse crearFamiliar(FamiliarRequest request) {
        Familiar familiar = new Familiar();
        familiar.setUsuarioId(user.getUsuarioId());
        familiar.setNombre(request.getNombre());
        familiar.setSegundoNombre(request.getSegundoNombre());
        familiar.setPrimerApellido(request.getPrimerApellido());
        familiar.setSegundoApellido(request.getSegundoApellido());
        familiar.setCedula(request.getCedula());
        familiar.setFechaExpedicionDocumento(
            request.getFechaExpedicionDocumento()
        );
        familiar.setFechaNacimiento(request.getFechaNacimiento());
        familiar.setMunicipio(request.getMunicipio());
        familiar.setDireccion(request.getDireccion());
        familiar.setBarrio(request.getBarrio());
        familiar.setTelefono(request.getTelefono());
        familiar.setCorreo(request.getCorreo());
        familiar.setNivelEducativo(request.getNivelEducativo());
        familiar.setOcupacion(request.getOcupacion());
        familiar.setTipoFamiliar(request.getTipoFamiliar());

        Familiar familiarSaved = familiarRepository.save(familiar);

        return FamiliarResponse.builder()
            .acudienteId(familiarSaved.getAcudienteId())
            .usuarioId(familiarSaved.getUsuarioId())
            .nombre(familiarSaved.getNombre())
            .segundoNombre(familiarSaved.getSegundoNombre())
            .primerApellido(familiarSaved.getPrimerApellido())
            .segundoApellido(familiarSaved.getSegundoApellido())
            .cedula(familiarSaved.getCedula())
            .fechaExpedicionDocumento(
                familiarSaved.getFechaExpedicionDocumento()
            )
            .fechaNacimiento(familiarSaved.getFechaNacimiento())
            .municipio(familiarSaved.getMunicipio())
            .direccion(familiarSaved.getDireccion())
            .barrio(familiarSaved.getBarrio())
            .telefono(familiarSaved.getTelefono())
            .correo(familiarSaved.getCorreo())
            .nivelEducativo(familiarSaved.getNivelEducativo())
            .ocupacion(familiarSaved.getOcupacion())
            .tipoFamiliar(familiarSaved.getTipoFamiliar())
            .createdAt(familiarSaved.getCreatedAt())
            .updatedAt(familiarSaved.getUpdatedAt())
            .build();
    }
}
