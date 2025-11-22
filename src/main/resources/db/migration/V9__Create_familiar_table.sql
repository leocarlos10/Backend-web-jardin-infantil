CREATE TABLE familiar (
    id_familiar  BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    segundo_nombre VARCHAR(100),
    primer_apellido VARCHAR(100) NOT NULL,
    segundo_apellido VARCHAR(100),
    cedula VARCHAR(50) NOT NULL UNIQUE,
    fecha_expedicion_documento DATE NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    municipio VARCHAR(100),
    direccion VARCHAR(255),
    barrio VARCHAR(100),
    telefono VARCHAR(50),
    correo VARCHAR(255) UNIQUE,
    nivel_educativo VARCHAR(100),
    ocupacion VARCHAR(100),
    tipo_familiar VARCHAR(50),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_familiar FOREIGN KEY (estudiante_id) REFERENCES estudiante(estudiante_id)

);
