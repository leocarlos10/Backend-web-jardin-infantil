
CREATE TABLE encuesta_satisfaccion (
    id_encuesta BIGINT AUTO_INCREMENT PRIMARY KEY,
    acudiente_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    nombre_respondedor VARCHAR(255) NOT NULL,
    respuestas TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_encuesta_acudiente FOREIGN KEY (acudiente_id) REFERENCES acudiente(acudiente_id)
);
