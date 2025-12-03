package com.jardininfantil.web_institucional.pattern.observer;

import com.jardininfantil.web_institucional.pattern.observer.listeners.EmailNotificationListener;
import com.jardininfantil.web_institucional.pattern.observer.listeners.LoggingListener;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import java.util.Set; 

/**
 * Configuración del patrón Observer
 * Registra todos los listeners a los eventos del sistema
 */
@Configuration
@RequiredArgsConstructor
public class ObserverConfig {

    private final EventManager eventManager;
    private final LoggingListener loggingListener;
    private final EmailNotificationListener emailListener;

    @PostConstruct
    public void setupListeners() {
        // Suscribir LoggingListener a todos los eventos
        for (EventType eventType : EventType.values()) {
            eventManager.subscribe(eventType.getValue(), loggingListener);
        }

        // Suscribir EmailListener solo a eventos importantes
        var eventosConEmail = Set.of(
            EventType.RESERVA_APROBADA,
            EventType.RESERVA_RECHAZADA,
            EventType.MATRICULA_CREADA,
            EventType.MATRICULA_CANCELADA,
            EventType.PAGO_VERIFICADO,
            EventType.PAGO_RECHAZADO,
            EventType.REGISTRO_CREADO
        );

        eventosConEmail.forEach(et ->
            eventManager.subscribe(et.getValue(), emailListener)
        );
    }
}
