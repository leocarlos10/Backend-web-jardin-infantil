package com.jardininfantil.web_institucional.pattern.observer.listeners;

import com.jardininfantil.web_institucional.pattern.observer.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Listener concreto para estadísticas del sistema
 * Mantiene contadores de eventos para reportes y análisis
 */
@Component
public class StatisticsListener implements EventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(StatisticsListener.class);
    private final Map<String, Integer> eventCounters = new HashMap<>();

    @Override
    public void update(String eventType, Object data) {
        // Incrementar contador del evento
        eventCounters.merge(eventType, 1, Integer::sum);
        
        logger.info("📊 Estadística actualizada - {}: {} eventos totales", 
                    eventType, eventCounters.get(eventType));
        
        // Aquí se podría actualizar métricas en tiempo real
        updateMetrics(eventType);
    }

    private void updateMetrics(String eventType) {
        // Implementar actualización de métricas/dashboard
        logger.debug("Actualizando métricas para: {}", eventType);
    }

    /**
     * Obtiene el contador de un tipo de evento específico
     */
    public int getEventCount(String eventType) {
        return eventCounters.getOrDefault(eventType, 0);
    }

    /**
     * Obtiene todos los contadores de eventos
     */
    public Map<String, Integer> getAllEventCounts() {
        return new HashMap<>(eventCounters);
    }
}
