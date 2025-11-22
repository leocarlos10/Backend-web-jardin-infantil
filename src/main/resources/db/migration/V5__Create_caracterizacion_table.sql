
CREATE TABLE caracterizacion_estudiante (
    id_caracterizacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    beneficiario_renta_ciudadana BOOLEAN NOT NULL,
    numero_miembro_familia INT NOT NULL,
    estrato_sisben VARCHAR(50),
    nivel_sisben VARCHAR(50),
    eps VARCHAR(100),
    regimen_contributivo VARCHAR(100),
    regimen_subsidiado VARCHAR(100),
    pertenece_comunidad_indigena BOOLEAN NOT NULL,
    nombre_co_indigena VARCHAR(255),
    desplazado BOOLEAN NOT NULL,
    municipio_expulsor VARCHAR(255),
    discapacidad BOOLEAN NOT NULL,
    tipo_discapacidad VARCHAR(255),
    hijo_unico BOOLEAN NOT NULL,
    enfermedades VARCHAR(255),
    viene_colegio_privado BOOLEAN NOT NULL,
    nombre_colegio VARCHAR(255),
    viene_hogar_icbf BOOLEAN NOT NULL,
    nombre_hogar_icbf VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_caracterizacion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(estudiante_id)
);
