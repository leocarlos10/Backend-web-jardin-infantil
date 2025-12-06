CREATE  PROCEDURE sp_get_dashboard_stats()
BEGIN
    SELECT
        (SELECT COUNT(*) FROM matricula ) AS total_matriculas,
        (SELECT COUNT(*) FROM reserva_cupo ) AS total_reservas,
        (SELECT COUNT(*) FROM estudiante) AS total_estudiantes,
        (SELECT COUNT(*) FROM encuesta_satisfaccion) AS total_encuestas;
END;
