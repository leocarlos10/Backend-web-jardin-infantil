CREATE TABLE cancelar_matricula (
    id_cancelar_matricula BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_matricula BIGINT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    fecha_retiro DATE NOT NULL,
    documento_soporte VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_cancelar_matricula FOREIGN KEY (id_matricula) REFERENCES matricula(id_matricula)
);
