CREATE TABLE matricula (
    id_matricula BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    grado VARCHAR(100) NOT NULL,
    valor_total DECIMAL(12,2) NOT NULL,
    contrato_firmado DECIMAL(12,2) NOT NULL,
    estado_matricula ENUM('ACTIVA','INACTIVA','CANCELADA') NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_matricula_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(estudiante_id)
);
