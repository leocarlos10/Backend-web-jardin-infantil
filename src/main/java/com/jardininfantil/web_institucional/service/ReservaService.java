package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.reserva.ReservaRequest;
import com.jardininfantil.web_institucional.dto.reserva.ReservaResponse;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.Estudiante;
import com.jardininfantil.web_institucional.models.Reserva;
import com.jardininfantil.web_institucional.models.enums.EstadoReserva;
import com.jardininfantil.web_institucional.pattern.observer.EventManager;
import com.jardininfantil.web_institucional.pattern.observer.EventType;
import com.jardininfantil.web_institucional.repository.EstudianteRepository;
import com.jardininfantil.web_institucional.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final EstudianteRepository estudianteRepository;
    private final EventManager eventManager;

    public ReservaService(ReservaRepository reservaRepository, 
                         EstudianteRepository estudianteRepository,
                         EventManager eventManager) {
        this.reservaRepository = reservaRepository;
        this.estudianteRepository = estudianteRepository;
        this.eventManager = eventManager;
    }

    public ReservaResponse crearReserva(ReservaRequest request) {
        // Verificar que el estudiante existe
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        Reserva reserva = new Reserva();
        reserva.setEstudianteId(request.getEstudianteId());
        reserva.setGradoSolicitado(request.getGradoSolicitado());
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        Reserva savedReserva = reservaRepository.save(reserva);
        
        eventManager.notify(EventType.RESERVA_CREADA.getValue(), savedReserva);
        return mapToResponse(savedReserva, estudiante.getNombre() + " " + estudiante.getPrimerApellido());
    }

    public ReservaResponse obtenerReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        Estudiante estudiante = estudianteRepository.findById(reserva.getEstudianteId())
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        return mapToResponse(reserva, estudiante.getNombre() + " " + estudiante.getPrimerApellido());
    }

    public List<ReservaResponse> listarTodasReservas() {
        return reservaRepository.findAll().stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    public List<ReservaResponse> listarReservasPorEstudiante(Long estudianteId) {
        List<Reserva> reservas = reservaRepository.findByEstudianteId(estudianteId);
        return reservas.stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    public ReservaResponse aprobarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        reserva.setEstadoReserva(EstadoReserva.ACEPTADA);
        reservaRepository.update(reserva);

        eventManager.notify(EventType.RESERVA_APROBADA.getValue(), reserva);

        return mapToResponseSimple(reserva);
    }

    public ReservaResponse rechazarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        reserva.setEstadoReserva(EstadoReserva.RECHAZADA);
        reservaRepository.update(reserva);

        eventManager.notify(EventType.RESERVA_RECHAZADA.getValue(), reserva);

        return mapToResponseSimple(reserva);
    }

    private ReservaResponse mapToResponse(Reserva reserva, String nombreEstudiante) {
        return ReservaResponse.builder()
                .idReserva(reserva.getIdReserva())
                .estudianteId(reserva.getEstudianteId())
                .nombreEstudiante(nombreEstudiante)
                .estadoReserva(reserva.getEstadoReserva().name())
                .gradoSolicitado(reserva.getGradoSolicitado())
                .createdAt(reserva.getCreatedAt())
                .updatedAt(reserva.getUpdatedAt())
                .build();
    }

    private ReservaResponse mapToResponseSimple(Reserva reserva) {
        String nombreEstudiante = estudianteRepository.findById(reserva.getEstudianteId())
                .map(e -> e.getNombre() + " " + e.getPrimerApellido())
                .orElse("Desconocido");

        return mapToResponse(reserva, nombreEstudiante);
    }
}
