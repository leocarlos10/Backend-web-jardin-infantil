package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.Familiar;
import com.jardininfantil.web_institucional.repository.FamiliarRepository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class FamiliarRepositoryImpl implements FamiliarRepository {

    private final JdbcTemplate jdbcTemplate;

    public FamiliarRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Familiar save(Familiar familiar) {
        String sql = """
                INSERT INTO acudiente (
                    estudiante_id,
                    nombre,
                    segundo_nombre,
                    primer_apellido,
                    segundo_apellido,
                    cedula,
                    fecha_expedicion_documento,
                    fecha_nacimiento,
                    municipio,
                    direccion,
                    barrio,
                    telefono,
                    correo,
                    nivel_educativo,
                    ocupacion,
                    tipo_familiar
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)
            """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, familiar.getEstudiante_id());
                ps.setString(2, familiar.getNombre());
                ps.setString(3, familiar.getSegundo_nombre());
                ps.setString(4, familiar.getPrimer_apellido());
                ps.setString(5, familiar.getSegundo_apellido());
                ps.setString(6, familiar.getCedula());
                ps.setString(7, familiar.getFecha_expedicion_documento());
                ps.setString(8, familiar.getFecha_nacimiento());
                ps.setLong(9, familiar.getMunicipio());
                ps.setString(10, familiar.getDireccion());
                ps.setString(11, familiar.getBarrio());
                ps.setString(12, familiar.getTelefono());
                ps.setString(13, familiar.getCorreo());
                ps.setString(14, familiar.getNivel_educativo());
                ps.setString(15, familiar.getocupacion());
                ps.setString(16, familiar.tipo_familiar());
                return ps;
            },
            keyHolder
        );

        familiar.setAcudienteId(keyHolder.getKey().longValue());
        return familiar;
    }
}
