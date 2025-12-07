package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.CaracterizacionEstudiante;
import com.jardininfantil.web_institucional.repository.CaracterizacionRepository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CaracterizacionRepositoryImpl
    implements CaracterizacionRepository {

    private final JdbcTemplate jdbcTemplate;

    public CaracterizacionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CaracterizacionEstudiante save(
        CaracterizacionEstudiante caracterizacion
    ) {
        String sql = """
            INSERT INTO caracterizacion_estudiante (
                estudiante_id,
                beneficiario_renta_ciudadana,
                numero_miembro_familia,
                estrato_sisben,
                nivel_sisben,
                eps,
                regimen_contributivo,
                regimen_subsidiado,
                pertenece_comunidad_indigena,
                nombre_co_indigena,
                desplazado,
                municipio_expulsor,
                discapacidad,
                tipo_discapacidad,
                hijo_unico,
                enfermedades,
                viene_colegio_privado,
                nombre_colegio,
                viene_hogar_icbf,
                nombre_hogar_icbf

                ) VALUES (?, ?, ?, ?, ?, ?,?,?,?,?,?, ?, ?, ?, ?, ?,?,?,?,?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
                );

                int index = 1;
                ps.setLong(index++, caracterizacion.getEstudiante_id());
                ps.setBoolean(
                    index++,
                    caracterizacion.isBeneficiario_renta_ciudadana()
                );
                ps.setInt(index++, caracterizacion.getNumero_miembro_familia());

                // Campos que pueden ser null si no se seleccionaron
                ps.setString(index++, caracterizacion.getEstrato_sisben());
                ps.setString(index++, caracterizacion.getNivel_sisben());
                ps.setString(index++, caracterizacion.getEPS());
                ps.setString(
                    index++,
                    caracterizacion.getRegimen_contributivo()
                );
                ps.setString(index++, caracterizacion.getRegimen_subsidiado());

                // Booleanos: si no se seleccionó, será false por defecto
                ps.setBoolean(
                    index++,
                    caracterizacion.isPertenece_comunidad_indigena()
                );
                ps.setString(index++, caracterizacion.getNombre_co_indigena());

                ps.setBoolean(index++, caracterizacion.isDesplazado());
                ps.setString(index++, caracterizacion.getMunicipio_expulsor());

                ps.setBoolean(index++, caracterizacion.isDiscapacidad());
                ps.setString(index++, caracterizacion.getTipo_discapacidad());

                ps.setBoolean(index++, caracterizacion.isHijo_unico());
                ps.setString(index++, caracterizacion.getEnfermedades());

                ps.setBoolean(
                    index++,
                    caracterizacion.isViene_colegio_privado()
                );
                ps.setString(index++, caracterizacion.getNombre_colegio());

                ps.setBoolean(index++, caracterizacion.isViene_hogar_ICBF());
                ps.setString(index++, caracterizacion.getNombre_hogar_ICBF());

                return ps;
            },
            keyHolder
        );

        caracterizacion.setId_caracterizacion(keyHolder.getKey().longValue());
        return caracterizacion;
    }
}
