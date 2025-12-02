package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.dto.reserva.ReservaRequest;
import com.jardininfantil.web_institucional.dto.reserva.ReservaResponse;
import com.jardininfantil.web_institucional.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Response<ReservaResponse>> crearReserva(@Valid @RequestBody ReservaRequest request) {
        ReservaResponse reserva = reservaService.crearReserva(request);
        Response<ReservaResponse> response = Response.<ReservaResponse>builder()
                .responseCode(HttpStatus.CREATED.value())
                .responseMessage("Reserva creada exitosamente")
                .data(reserva)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<List<ReservaResponse>>> listarTodasReservas() {
        List<ReservaResponse> reservas = reservaService.listarTodasReservas();
        Response<List<ReservaResponse>> response = Response.<List<ReservaResponse>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Lista de reservas obtenida")
                .data(reservas)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ReservaResponse>> obtenerReserva(@PathVariable Long id) {
        ReservaResponse reserva = reservaService.obtenerReserva(id);
        Response<ReservaResponse> response = Response.<ReservaResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Reserva encontrada")
                .data(reserva)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<Response<List<ReservaResponse>>> listarReservasPorEstudiante(
            @PathVariable Long estudianteId) {
        List<ReservaResponse> reservas = reservaService.listarReservasPorEstudiante(estudianteId);
        Response<List<ReservaResponse>> response = Response.<List<ReservaResponse>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Reservas del estudiante obtenidas")
                .data(reservas)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<ReservaResponse>> aprobarReserva(@PathVariable Long id) {
        ReservaResponse reserva = reservaService.aprobarReserva(id);
        Response<ReservaResponse> response = Response.<ReservaResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Reserva aprobada exitosamente")
                .data(reserva)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/rechazar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<ReservaResponse>> rechazarReserva(@PathVariable Long id) {
        ReservaResponse reserva = reservaService.rechazarReserva(id);
        Response<ReservaResponse> response = Response.<ReservaResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Reserva rechazada")
                .data(reserva)
                .build();
        return ResponseEntity.ok(response);
    }
}
