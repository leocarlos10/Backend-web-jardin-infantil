package com.jardininfantil.web_institucional.pattern.observer;

/**
 * Enumeración de tipos de eventos del sistema
 * Define todos los eventos que pueden ser observados
 */
public enum EventType {
    RESERVA_CREADA("reserva.creada"),
    RESERVA_APROBADA("reserva.aprobada"),
    RESERVA_RECHAZADA("reserva.rechazada"),
    REGISTRO_CREADO("registro.creado"),
    MATRICULA_CREADA("matricula.creada"),
    MATRICULA_CANCELADA("matricula.cancelada"),
    PAGO_REGISTRADO("pago.registrado"),
    PAGO_VERIFICADO("pago.verificado"),
    PAGO_RECHAZADO("pago.rechazado"),
    ESTUDIANTE_CREADO("estudiante.creado"),
    ESTUDIANTE_ACTUALIZADO("estudiante.actualizado");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
