CREATE TABLE acudiente (
    acudiente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    segundo_nombre VARCHAR(100),
    primer_apellido VARCHAR(100) NOT NULL,
    segundo_apellido VARCHAR(100),
    cedula VARCHAR(50) UNIQUE NOT NULL,
    fecha_expedicion_documento DATE,
    fecha_nacimiento DATE,
    municipio VARCHAR(100),
    direccion TEXT,
    barrio VARCHAR(100),
    telefono VARCHAR(20),
    correo VARCHAR(255),
    nivel_educativo VARCHAR(100),
    ocupacion VARCHAR(100),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_acudiente_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
);
