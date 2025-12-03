package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.estudiante.EstudianteRequest;
import com.jardininfantil.web_institucional.dto.estudiante.EstudianteResponse;
import com.jardininfantil.web_institucional.exception.DataExistException;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.Acudiente;
import com.jardininfantil.web_institucional.models.Estudiante;
import com.jardininfantil.web_institucional.pattern.observer.EventManager;
import com.jardininfantil.web_institucional.pattern.observer.EventType;
import com.jardininfantil.web_institucional.repository.AcudienteRepository;
import com.jardininfantil.web_institucional.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final AcudienteRepository acudienteRepository;
    private final EventManager eventManager;

    public EstudianteService(EstudianteRepository estudianteRepository, 
                            AcudienteRepository acudienteRepository,
                            EventManager eventManager) {
        this.estudianteRepository = estudianteRepository;
        this.acudienteRepository = acudienteRepository;
        this.eventManager = eventManager;
    }

    public EstudianteResponse crearEstudiante(EstudianteRequest request) {
        // Verificar que el acudiente existe
        Acudiente acudiente = acudienteRepository.findById(request.getAcudienteId())
                .orElseThrow(() -> new NotFoundException("Acudiente no encontrado"));

        // Verificar que no existe un estudiante con el mismo documento (si se
        // proporciona)
        if (request.getNumeroRegistroCivil() != null && !request.getNumeroRegistroCivil().isEmpty()) {
            estudianteRepository.findByNumeroDocumento(request.getNumeroRegistroCivil())
                    .ifPresent(e -> {
                        throw new DataExistException(
                                "Ya existe un estudiante con el documento " + request.getNumeroRegistroCivil());
                    });
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setAcudienteId(request.getAcudienteId());
        estudiante.setNombre(request.getNombre());
        estudiante.setSegundoNombre(request.getSegundoNombre());
        estudiante.setPrimerApellido(request.getPrimerApellido());
        estudiante.setSegundoApellido(request.getSegundoApellido());
        estudiante.setNumeroRegistroCivil(request.getNumeroRegistroCivil());
        estudiante.setFechaExp(request.getFechaExp());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setTipoSangre(request.getTipoSangre());
        estudiante.setSexo(request.getSexo());
        estudiante.setCorreoPadres(request.getCorreoPadres());
        estudiante.setEdad(request.getEdad());
        estudiante.setLugarNacimiento(request.getLugarNacimiento());
        estudiante.setMunicipio(request.getMunicipio());
        estudiante.setDepartamento(request.getDepartamento());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setBarrio(request.getBarrio());
        estudiante.setTipoEstudiante(request.getTipoEstudiante());

        Estudiante savedEstudiante = estudianteRepository.save(estudiante);
        
        // Notificar evento usando patrón Observer
        eventManager.notify(EventType.ESTUDIANTE_CREADO.getValue(), savedEstudiante);
        
        return mapToResponse(savedEstudiante, acudiente.getNombre() + " " + acudiente.getApellido());
    }

    public EstudianteResponse obtenerEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        return mapToResponseSimple(estudiante);
    }

    public List<EstudianteResponse> listarTodosEstudiantes() {
        return estudianteRepository.findAll().stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    public List<EstudianteResponse> listarEstudiantesPorAcudiente(Long acudienteId) {
        return estudianteRepository.findByAcudienteId(acudienteId).stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    public EstudianteResponse actualizarEstudiante(Long id, EstudianteRequest request) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        estudiante.setNombre(request.getNombre());
        estudiante.setSegundoNombre(request.getSegundoNombre());
        estudiante.setPrimerApellido(request.getPrimerApellido());
        estudiante.setSegundoApellido(request.getSegundoApellido());
        estudiante.setNumeroRegistroCivil(request.getNumeroRegistroCivil());
        estudiante.setFechaExp(request.getFechaExp());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setTipoSangre(request.getTipoSangre());
        estudiante.setSexo(request.getSexo());
        estudiante.setCorreoPadres(request.getCorreoPadres());
        estudiante.setEdad(request.getEdad());
        estudiante.setLugarNacimiento(request.getLugarNacimiento());
        estudiante.setMunicipio(request.getMunicipio());
        estudiante.setDepartamento(request.getDepartamento());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setBarrio(request.getBarrio());
        estudiante.setTipoEstudiante(request.getTipoEstudiante());

        estudianteRepository.update(estudiante);
        
        // Notificar evento usando patrón Observer
        eventManager.notify(EventType.ESTUDIANTE_ACTUALIZADO.getValue(), estudiante);
        
        return mapToResponseSimple(estudiante);
    }

    public void eliminarEstudiante(Long id) {
        if (!estudianteRepository.findById(id).isPresent()) {
            throw new NotFoundException("Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }

    private EstudianteResponse mapToResponse(Estudiante estudiante, String nombreAcudiente) {
        return EstudianteResponse.builder()
                .estudianteId(estudiante.getEstudianteId())
                .acudienteId(estudiante.getAcudienteId())
                .nombreAcudiente(nombreAcudiente)
                .nombre(estudiante.getNombre())
                .segundoNombre(estudiante.getSegundoNombre())
                .primerApellido(estudiante.getPrimerApellido())
                .segundoApellido(estudiante.getSegundoApellido())
                .numeroRegistroCivil(estudiante.getNumeroRegistroCivil())
                .fechaExp(estudiante.getFechaExp())
                .fechaNacimiento(estudiante.getFechaNacimiento())
                .tipoSangre(estudiante.getTipoSangre())
                .sexo(estudiante.getSexo())
                .correoPadres(estudiante.getCorreoPadres())
                .edad(estudiante.getEdad())
                .lugarNacimiento(estudiante.getLugarNacimiento())
                .municipio(estudiante.getMunicipio())
                .departamento(estudiante.getDepartamento())
                .direccion(estudiante.getDireccion())
                .barrio(estudiante.getBarrio())
                .tipoEstudiante(estudiante.getTipoEstudiante())
                .createdAt(estudiante.getCreatedAt())
                .updatedAt(estudiante.getUpdatedAt())
                .build();
    }

    private EstudianteResponse mapToResponseSimple(Estudiante estudiante) {
        String nombreAcudiente = acudienteRepository.findById(estudiante.getAcudienteId())
                .map(a -> a.getNombre() + " " + a.getApellido())
                .orElse("Desconocido");

        return mapToResponse(estudiante, nombreAcudiente);
    }
}
