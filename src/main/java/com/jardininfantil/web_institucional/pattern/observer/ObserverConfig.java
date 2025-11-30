package com.jardininfantil.web_institucional.pattern.observer;

import com.jardininfantil.web_institucional.pattern.observer.listeners.EmailNotificationListener;
import com.jardininfantil.web_institucional.pattern.observer.listeners.LoggingListener;
import com.jardininfantil.web_institucional.pattern.observer.listeners.StatisticsListener;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuración del patrón Observer
 * Registra todos los listeners a los eventos del sistema
 */
@Configuration
public class ObserverConfig {

    private final EventManager eventManager;
    private final LoggingListener loggingListener;
    private final EmailNotificationListener emailListener;
    private final StatisticsListener statisticsListener;

    public ObserverConfig(EventManager eventManager,
                         LoggingListener loggingListener,
                         EmailNotificationListener emailListener,
                         StatisticsListener statisticsListener) {
        this.eventManager = eventManager;
        this.loggingListener = loggingListener;
        this.emailListener = emailListener;
        this.statisticsListener = statisticsListener;
    }

    @PostConstruct
    public void setupListeners() {
        // Suscribir LoggingListener a todos los eventos
        for (EventType eventType : EventType.values()) {
            eventManager.subscribe(eventType.getValue(), loggingListener);
            eventManager.subscribe(eventType.getValue(), statisticsListener);
        }

        // Suscribir EmailListener solo a eventos importantes
        eventManager.subscribe(EventType.RESERVA_APROBADA.getValue(), emailListener);
        eventManager.subscribe(EventType.RESERVA_RECHAZADA.getValue(), emailListener);
        eventManager.subscribe(EventType.MATRICULA_CREADA.getValue(), emailListener);
        eventManager.subscribe(EventType.PAGO_VERIFICADO.getValue(), emailListener);
        eventManager.subscribe(EventType.PAGO_RECHAZADO.getValue(), emailListener);
    }
}
