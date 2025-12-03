package com.jardininfantil.web_institucional.pattern.observer.listeners;

import com.jardininfantil.web_institucional.pattern.observer.EventListener;
import com.jardininfantil.web_institucional.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Listener responsable de enviar emails automáticos
 * cuando ocurren eventos importantes en el sistema.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EmailNotificationListener implements EventListener {
    
    private final EmailService emailService;
    
    @Override
    public void update(String eventType, Object data) {
        switch (eventType) {
            case "reserva.aprobada" -> sendReservaAprobadaEmail(data);
            case "reserva.rechazada" -> sendReservaRechazadaEmail(data);
            case "matricula.creada" -> sendMatriculaCreadaEmail(data);
            case "matricula.cancelada" -> sendMatriculaCanceladaEmail(data);
            case "pago.verificado" -> sendPagoVerificadoEmail(data);
            case "pago.rechazado" -> sendPagoRechazadoEmail(data);
            case "registro.creado" -> sendBienvenidaRegistroEmail(data);
            case "cupo.agotado" -> sendCupoAgotadoEmail(data);
            case "lista_espera.avanza" -> sendListaEsperaAvanzaEmail(data);
            default -> log.debug(
                "No hay plantilla de email configurada para el evento: {}",
                eventType
            );
        }
    }

    private void sendBienvenidaRegistroEmail(Object data) {
        emailService.sendVerificationEmail(data);
    }
    
    private void sendReservaAprobadaEmail(Object data) {
        log.info("Enviando email: Reserva aprobada");
    }

    private void sendReservaRechazadaEmail(Object data) {
        log.info("Enviando email: Reserva rechazada");
    }

    private void sendMatriculaCreadaEmail(Object data) {
        log.info("Enviando email: Matrícula creada – Bienvenida oficial");
    }

    private void sendMatriculaCanceladaEmail(Object data) {
        log.info("Enviando email: Matrícula cancelada");
    }

    private void sendPagoVerificadoEmail(Object data) {
        log.info("Enviando email: Pago verificado + comprobante");
    }

    private void sendPagoRechazadoEmail(Object data) {
        log.info("Enviando email: Pago rechazado – solicita nuevo comprobante");
    }


    private void sendCupoAgotadoEmail(Object data) {
        log.info(
            "Enviando email: Cupo agotado – te agregamos a lista de espera"
        );
    }

    private void sendListaEsperaAvanzaEmail(Object data) {
        log.info(
            "Enviando email URGENTE: Se liberó un cupo – tienes 24h para confirmar"
        );
    }
}
