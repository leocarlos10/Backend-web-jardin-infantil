package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.matricula.MatriculaRequest;
import com.jardininfantil.web_institucional.dto.matricula.MatriculaResponse;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.Estudiante;
import com.jardininfantil.web_institucional.models.Matricula;
import com.jardininfantil.web_institucional.models.Usuario;
import com.jardininfantil.web_institucional.models.Acudiente;
import com.jardininfantil.web_institucional.models.enums.EstadoMatricula;
import com.jardininfantil.web_institucional.pattern.observer.EventManager;
import com.jardininfantil.web_institucional.pattern.observer.EventType;
import com.jardininfantil.web_institucional.repository.EstudianteRepository;
import com.jardininfantil.web_institucional.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import com.jardininfantil.web_institucional.repository.AcudienteRepository;
import com.jardininfantil.web_institucional.repository.UserRepository;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final EventManager eventManager;
    private final UserRepository userRepository;
    private final AcudienteRepository acudienteRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
            EstudianteRepository estudianteRepository,
            EventManager eventManager ,
            UserRepository userRepository,
            AcudienteRepository acudienteRepository
    ) {
        this.matriculaRepository = matriculaRepository;
        this.estudianteRepository = estudianteRepository;
        this.eventManager = eventManager;
        this.userRepository = userRepository;
        this.acudienteRepository = acudienteRepository;
    }

    public MatriculaResponse crearMatricula(MatriculaRequest request) {
        // Verificar que el estudiante existe
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        Matricula matricula = new Matricula();
        matricula.setEstudianteId(request.getEstudianteId());
        matricula.setFecha(request.getFecha());
        matricula.setGrado(request.getGrado());
        matricula.setValorTotal(request.getValorTotal());
        matricula.setContratoFirmado(request.getContratoFirmado());
        matricula.setEstadoMatricula(EstadoMatricula.ACTIVA);

        Matricula savedMatricula = matriculaRepository.save(matricula);

        // Notificar evento usando patrón Observer
        eventManager.notify(EventType.MATRICULA_CREADA.getValue(), savedMatricula);

        return mapToResponse(savedMatricula, estudiante.getNombre() + " " + estudiante.getPrimerApellido());
    }

    public MatriculaResponse obtenerMatricula(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Matrícula no encontrada"));

        Estudiante estudiante = estudianteRepository.findById(matricula.getEstudianteId())
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        return mapToResponse(matricula, estudiante.getNombre() + " " + estudiante.getPrimerApellido());
    }

    public List<MatriculaResponse> listarTodasMatriculas() {
        return matriculaRepository.findAll().stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    public List<MatriculaResponse> listarMatriculasPorEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        return matriculas.stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    public MatriculaResponse actualizarMatricula(Long id, MatriculaRequest request) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Matrícula no encontrada"));

        matricula.setEstadoMatricula(EstadoMatricula.valueOf(request.getEstadoMatricula()));

        matriculaRepository.update(matricula);
        return mapToResponseSimple(matricula);
    }

    public MatriculaResponse cancelarMatricula(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Matrícula no encontrada"));

        matricula.setEstadoMatricula(EstadoMatricula.CANCELADA);
        matriculaRepository.update(matricula);

        // Notificar evento usando patrón Observer
        eventManager.notify(EventType.MATRICULA_CANCELADA.getValue(), matricula);

        return mapToResponseSimple(matricula);
    }

    
    public List<MatriculaResponse> misMatriculas(Authentication authentication) {
        String userEmail = authentication.getName();
        Usuario user = userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Acudiente acudiente = acudienteRepository
            .findByUsuarioId(user.getUsuarioId())
            .orElseThrow(() ->
                new NotFoundException("Acudiente no encontrado")
            );

        List<Estudiante> estudiantes = estudianteRepository.findByAcudienteId(
            acudiente.getAcudienteId()
        );
        if (estudiantes.isEmpty()) {
            throw new NotFoundException("Estudiante no encontrado");
        }

        return estudiantes
            .stream()
            .flatMap(est ->
                matriculaRepository.verMisMatriculas(est.getEstudianteId()).stream()
            )
            .map(this::mapToResponseSimple)
            .collect(Collectors.toList());
    }
    
    private MatriculaResponse mapToResponse(Matricula matricula, String nombreEstudiante) {
        return MatriculaResponse.builder()
                .idMatricula(matricula.getIdMatricula())
                .estudianteId(matricula.getEstudianteId())
                .nombreEstudiante(nombreEstudiante)
                .fecha(matricula.getFecha())
                .grado(matricula.getGrado())
                .valorTotal(matricula.getValorTotal())
                .contratoFirmado(matricula.getContratoFirmado())
                .estadoMatricula(matricula.getEstadoMatricula().name())
                .createdAt(matricula.getCreatedAt())
                .updatedAt(matricula.getUpdatedAt())
                .build();
    }

    private MatriculaResponse mapToResponseSimple(Matricula matricula) {
        String nombreEstudiante = estudianteRepository.findById(matricula.getEstudianteId())
                .map(e -> e.getNombre() + " " + e.getPrimerApellido())
                .orElse("Desconocido");

        return mapToResponse(matricula, nombreEstudiante);
    }
}
