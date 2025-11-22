CREATE TABLE reserva_cupo (
    id_reserva BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    estado_reserva ENUM('ACEPTADA','RECHAZADA') NOT NULL,
    grado_solicitado VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,


    CONSTRAINT fk_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(estudiante_id)
);
