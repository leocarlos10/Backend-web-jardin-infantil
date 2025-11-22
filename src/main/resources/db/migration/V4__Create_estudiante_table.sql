CREATE TABLE estudiante (
    estudiante_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    acudiente_id BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    segundo_nombre VARCHAR(100),
    primer_apellido VARCHAR(100) NOT NULL,
    segundo_apellido VARCHAR(100),
    numero_registro_civil VARCHAR(100) UNIQUE,
    fecha_exp DATE,
    fecha_nacimiento DATE NOT NULL,
    tipo_sangre INT,
    sexo ENUM('M', 'F') NOT NULL,
    correo_padres VARCHAR(255),
    edad INT,
    lugar_nacimiento VARCHAR(100),
    municipio VARCHAR(100),
    departamento VARCHAR(100),
    direccion TEXT,
    barrio VARCHAR(100),
    tipo_estudiante VARCHAR(100),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_acudiente FOREIGN KEY(acudiente_id) REFERENCES acudiente(acudiente_id)
    
);
