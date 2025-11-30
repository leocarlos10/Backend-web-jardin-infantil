package com.jardininfantil.web_institucional.pattern.observer.listeners;

import com.jardininfantil.web_institucional.pattern.observer.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Listener concreto para notificaciones por email
 * Se activa cuando ocurren eventos importantes en el sistema
 */
@Component
public class EmailNotificationListener implements EventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationListener.class);

    @Override
    public void update(String eventType, Object data) {
        // Aquí se implementaría el envío real de emails
        logger.info("📧 Email enviado para evento: {} con datos: {}", eventType, data);
        
        // Ejemplo de lógica según el tipo de evento
        switch (eventType) {
            case "reserva.aprobada":
                sendReservaAprobadaEmail(data);
                break;
            case "pago.verificado":
                sendPagoVerificadoEmail(data);
                break;
            case "matricula.creada":
                sendMatriculaCreadaEmail(data);
                break;
            default:
                logger.info("Evento no manejado para email: {}", eventType);
        }
    }

    private void sendReservaAprobadaEmail(Object data) {
        logger.info("Enviando email de reserva aprobada...");
        // Implementar envío de email con plantilla
    }

    private void sendPagoVerificadoEmail(Object data) {
        logger.info("Enviando email de pago verificado...");
        // Implementar envío de email con comprobante
    }

    private void sendMatriculaCreadaEmail(Object data) {
        logger.info("Enviando email de matrícula creada...");
        // Implementar envío de email de bienvenida
    }
}
