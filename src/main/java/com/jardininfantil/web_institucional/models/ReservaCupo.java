package com.jardininfantil.web_institucional.models;

import com.jardininfantil.web_institucional.models.enums.EstadoReserva;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class ReservaCupo {

    @Getter
    @Setter
    private Long id_reserva;

    @Getter
    @Setter
    private String estudiante_id;

    @Getter
    @Setter
    private String grado_solicitado;

    @Getter
    @Setter
    private LocalDate fecha;

    @Getter
    @Setter
    private EstadoReserva estado_reserva;

    // Constructor vacío
    public ReservaCupo() {}

    // Constructor con todos los parámetros
    public ReservaCupo(
        Long id_reserva,
        String estudiante_id,
        String grado_solicitado,
        LocalDate fecha,
        EstadoReserva estado_reserva
    ) {
        this.id_reserva = id_reserva;
        this.estudiante_id = estudiante_id;
        this.grado_solicitado = grado_solicitado;
        this.fecha = fecha;
        this.estado_reserva = estado_reserva;
    }
}
