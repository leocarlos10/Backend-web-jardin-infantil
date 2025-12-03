package com.jardininfantil.web_institucional.pattern.observer;

/**
 * Interfaz Observer - Define el contrato para los observadores
 * que serán notificados cuando ocurran eventos en el sistema
 */
public interface EventListener {
    /**
     * Método que será llamado cuando ocurra un evento
     * @param eventType Tipo de evento que ocurrió
     * @param data Datos relacionados con el evento
     */
    void update(String eventType, Object data);
}
