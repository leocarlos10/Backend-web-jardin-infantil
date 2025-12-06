package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.acudiente.AcudienteRequest;
import com.jardininfantil.web_institucional.dto.acudiente.AcudienteResponse;
import com.jardininfantil.web_institucional.exception.DataExistException;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.Acudiente;
import com.jardininfantil.web_institucional.models.Usuario;
import com.jardininfantil.web_institucional.models.enums.RolUsuario;
import com.jardininfantil.web_institucional.repository.AcudienteRepository;
import com.jardininfantil.web_institucional.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AcudienteService {

    private final AcudienteRepository acudienteRepository;
    private final UserRepository userRepository;

    public AcudienteService(
        AcudienteRepository acudienteRepository,
        UserRepository userRepository
    ) {
        this.acudienteRepository = acudienteRepository;
        this.userRepository = userRepository;
    }

    public AcudienteResponse crearAcudiente(
        AcudienteRequest request,
        Authentication authentication
    ) {
        // get the userEmail from the login
        String userEmail = authentication.getName();

        // find the user by email for get the id
        Usuario user = userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // change user role USER to ACUDIENTE
        user.setTipo_Usuario(RolUsuario.ACUDIENTE);
        userRepository.updateRol(
            user.getUsuarioId(),
            user.getTipo_Usuario().toString()
        );

        if (request.getCedula() != null && !request.getCedula().isEmpty()) {
            acudienteRepository
                .findByNumeroDocumento(request.getCedula())
                .ifPresent(e -> {
                    throw new DataExistException(
                        "Ya existe un estudiante con el documento " +
                            request.getCedula()
                    );
                });
        }

        Acudiente acudiente = new Acudiente();
        acudiente.setUsuarioId(user.getUsuarioId());
        acudiente.setNombre(request.getNombre());
        acudiente.setSegundoNombre(request.getSegundoNombre());
        acudiente.setApellido(request.getApellido());
        acudiente.setSegundoApellido(request.getSegundoApellido());
        acudiente.setNumeroDocumento(request.getCedula());
        acudiente.setFechaExpedicion(request.getFechaExpedicion());
        acudiente.setFechaNacimiento(request.getFechaNacimiento());
        acudiente.setMunicipio(request.getMunicipio());
        acudiente.setDireccion(request.getDireccion());
        acudiente.setBarrio(request.getBarrio());
        acudiente.setTelefono(request.getTelefono());
        acudiente.setCorreo(request.getCorreo());
        acudiente.setNivelEducativo(request.getNivelEducativo());
        acudiente.setOcupacion(request.getOcupacion());

        Acudiente acudienteSaved = acudienteRepository.save(acudiente);

        return AcudienteResponse.builder()
            .acudienteId(acudienteSaved.getAcudienteId())
            .usuarioId(acudienteSaved.getUsuarioId())
            .nombre(acudienteSaved.getNombre())
            .segundoNombre(acudienteSaved.getSegundoNombre())
            .apellido(acudienteSaved.getApellido())
            .segundoApellido(acudienteSaved.getSegundoApellido())
            .cedula(acudienteSaved.getNumeroDocumento())
            //.fechaExpedicion(acudienteSaved.getFechaExpedicion())
            //.fechaNacimiento(acudienteSaved.getFechaNacimiento())
            //.municipio(acudienteSaved.getMunicipio())
            //.direccion(acudienteSaved.getDireccion())
            //.barrio(acudienteSaved.getBarrio())
            .telefono(acudienteSaved.getTelefono())
            .correo(acudienteSaved.getCorreo())
            //.nivelEducativo(acudienteSaved.getNivelEducativo())
            .ocupacion(acudienteSaved.getOcupacion())
            .build();
    }
}
