package com.jardininfantil.web_institucional.pattern.observer;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Clase Subject - Gestiona los observadores y notifica eventos
 * Implementa el patrón Observer para el sistema de notificaciones
 */
@Component
public class EventManager {
    
    private final Map<String, List<EventListener>> listeners = new HashMap<>();

    /**
     * Suscribe un listener a un tipo de evento específico
     * @param eventType Tipo de evento al que suscribirse
     * @param listener Listener que será notificado
     */
    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    /**
     * Desuscribe un listener de un tipo de evento
     * @param eventType Tipo de evento del que desuscribirse
     * @param listener Listener a desuscribir
     */
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    /**
     * Notifica a todos los listeners suscritos a un tipo de evento
     * @param eventType Tipo de evento que ocurrió
     * @param data Datos del evento
     */
    public void notify(String eventType, Object data) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            for (EventListener listener : users) {
                listener.update(eventType, data);
            }
        }
    }
}
