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

        // Verificar que no existe un estudiante con el mismo documento
        estudianteRepository.findByNumeroDocumento(request.getNumeroDocumento())
                .ifPresent(e -> {
                    throw new DataExistException(
                            "Ya existe un estudiante con el documento " + request.getNumeroDocumento());
                });

        Estudiante estudiante = new Estudiante();
        estudiante.setAcudienteId(request.getAcudienteId());
        estudiante.setNombre(request.getNombre());
        estudiante.setApellido(request.getApellido());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setTipoDocumento(request.getTipoDocumento());
        estudiante.setNumeroDocumento(request.getNumeroDocumento());
        estudiante.setGenero(request.getGenero());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setTelefono(request.getTelefono());

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
        estudiante.setApellido(request.getApellido());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setTipoDocumento(request.getTipoDocumento());
        estudiante.setNumeroDocumento(request.getNumeroDocumento());
        estudiante.setGenero(request.getGenero());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setTelefono(request.getTelefono());

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
                .apellido(estudiante.getApellido())
                .fechaNacimiento(estudiante.getFechaNacimiento())
                .tipoDocumento(estudiante.getTipoDocumento())
                .numeroDocumento(estudiante.getNumeroDocumento())
                .genero(estudiante.getGenero())
                .direccion(estudiante.getDireccion())
                .telefono(estudiante.getTelefono())
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
