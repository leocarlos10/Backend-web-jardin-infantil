package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.dto.pago.PagoRequest;
import com.jardininfantil.web_institucional.dto.pago.PagoResponse;
import com.jardininfantil.web_institucional.exception.NotFoundException;
import com.jardininfantil.web_institucional.models.PagoMatricula;
import com.jardininfantil.web_institucional.models.enums.EstadoPago;
import com.jardininfantil.web_institucional.pattern.observer.EventManager;
import com.jardininfantil.web_institucional.pattern.observer.EventType;
import com.jardininfantil.web_institucional.repository.MatriculaRepository;
import com.jardininfantil.web_institucional.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final MatriculaRepository matriculaRepository;
    private final EventManager eventManager;

    public PagoService(PagoRepository pagoRepository, 
                      MatriculaRepository matriculaRepository,
                      EventManager eventManager) {
        this.pagoRepository = pagoRepository;
        this.matriculaRepository = matriculaRepository;
        this.eventManager = eventManager;
    }

    public PagoResponse registrarPago(PagoRequest request) {
        // Verificar que la matrícula existe
        matriculaRepository.findById(request.getMatriculaId())
                .orElseThrow(() -> new NotFoundException("Matrícula no encontrada"));

        PagoMatricula pago = new PagoMatricula();
        pago.setMatriculaId(request.getMatriculaId());
        pago.setFechaPago(request.getFechaPago());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setReferencia(request.getReferencia());
        pago.setComprobante(request.getComprobante());
        pago.setEstadoPago(EstadoPago.PENDIENTE);

        PagoMatricula savedPago = pagoRepository.save(pago);
        
        eventManager.notify(EventType.PAGO_REGISTRADO.getValue(), savedPago);
        
        return mapToResponse(savedPago);
    }

    public PagoResponse obtenerPago(Long id) {
        PagoMatricula pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        return mapToResponse(pago);
    }

    public List<PagoResponse> listarPagosPorMatricula(Long matriculaId) {
        return pagoRepository.findByMatriculaId(matriculaId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PagoResponse> listarTodosPagos() {
        return pagoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PagoResponse verificarPago(Long id) {
        PagoMatricula pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        pago.setEstadoPago(EstadoPago.VERIFICADO);
        pagoRepository.update(pago);

        // Notificar evento usando patrón Observer
        eventManager.notify(EventType.PAGO_VERIFICADO.getValue(), pago);

        return mapToResponse(pago);
    }

    public PagoResponse rechazarPago(Long id) {
        PagoMatricula pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        pago.setEstadoPago(EstadoPago.RECHAZADO);
        pagoRepository.update(pago);

        // Notificar evento usando patrón Observer
        eventManager.notify(EventType.PAGO_RECHAZADO.getValue(), pago);

        return mapToResponse(pago);
    }

    private PagoResponse mapToResponse(PagoMatricula pago) {
        return PagoResponse.builder()
                .idPago(pago.getIdPago())
                .matriculaId(pago.getMatriculaId())
                .fechaPago(pago.getFechaPago())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodoPago())
                .referencia(pago.getReferencia())
                .estadoPago(pago.getEstadoPago().name())
                .comprobante(pago.getComprobante())
                .createdAt(pago.getCreatedAt())
                .updatedAt(pago.getUpdatedAt())
                .build();
    }
}
