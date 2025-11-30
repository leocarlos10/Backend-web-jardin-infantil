package com.jardininfantil.web_institucional.pattern.observer.listeners;

import com.jardininfantil.web_institucional.pattern.observer.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Listener concreto para registro de auditoría
 * Registra todos los eventos importantes del sistema
 */
@Component
public class LoggingListener implements EventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingListener.class);

    @Override
    public void update(String eventType, Object data) {
        String timestamp = LocalDateTime.now().toString();
        logger.info("📝 [AUDIT] {} - Evento: {} | Datos: {}", timestamp, eventType, data);
        
        // Aquí se podría guardar en una tabla de auditoría en la BD
        saveToAuditLog(eventType, data, timestamp);
    }

    private void saveToAuditLog(String eventType, Object data, String timestamp) {
        // Implementar guardado en tabla de auditoría
        logger.debug("Guardando en log de auditoría: {} - {}", eventType, timestamp);
    }
}
