package com.jardininfantil.web_institucional.repository;

import com.jardininfantil.web_institucional.models.Matricula;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository {
    Matricula save(Matricula matricula);

    Optional<Matricula> findById(Long id);

    List<Matricula> findAll();
    
    List<Matricula> verMisMatriculas(Long estudianteId);

    List<Matricula> findByEstudianteId(Long estudianteId);

    List<Matricula> findByEstadoMatricula(String estado);

    void update(Matricula matricula);

    void deleteById(Long id);
}
