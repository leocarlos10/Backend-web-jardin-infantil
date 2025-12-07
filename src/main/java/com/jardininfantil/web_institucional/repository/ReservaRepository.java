package com.jardininfantil.web_institucional.repository;

import com.jardininfantil.web_institucional.models.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository {
    Reserva save(Reserva reserva);

    Optional<Reserva> findById(Long id);

    List<Reserva> findAll();

    List<Reserva> findByEstudianteId(Long estudianteId);

    List<Reserva> verMisReservas(Long estudianteId);
    
    List<Reserva> findByEstadoReserva(String estado);

    void update(Reserva reserva);

    void deleteById(Long id);
}
