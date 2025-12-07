package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.Estudiante;
import com.jardininfantil.web_institucional.repository.EstudianteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class EstudianteRepositoryImpl implements EstudianteRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstudianteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Estudiante> rowMapper = (rs, rowNum) -> {
        Estudiante estudiante = new Estudiante();
        estudiante.setEstudianteId(rs.getLong("estudiante_id"));
        estudiante.setAcudienteId(rs.getLong("acudiente_id"));
        estudiante.setNombre(rs.getString("nombre"));
        estudiante.setSegundoNombre(rs.getString("segundo_nombre"));
        estudiante.setPrimerApellido(rs.getString("primer_apellido"));
        estudiante.setSegundoApellido(rs.getString("segundo_apellido"));
        estudiante.setNumeroRegistroCivil(rs.getString("numero_registro_civil"));
        estudiante.setFechaExp(rs.getDate("fecha_exp") != null ? rs.getDate("fecha_exp").toLocalDate() : null);
        estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        estudiante.setTipoSangre(rs.getString("tipo_sangre"));
        estudiante.setSexo(rs.getString("sexo"));
        estudiante.setCorreoPadres(rs.getString("correo_padres"));
        estudiante.setEdad(rs.getInt("edad"));
        estudiante.setLugarNacimiento(rs.getString("lugar_nacimiento"));
        estudiante.setMunicipio(rs.getString("municipio"));
        estudiante.setDepartamento(rs.getString("departamento"));
        estudiante.setDireccion(rs.getString("direccion"));
        estudiante.setBarrio(rs.getString("barrio"));
        estudiante.setTipoEstudiante(rs.getString("tipo_estudiante"));
        estudiante.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        estudiante.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return estudiante;
    };

    @Override
    public Estudiante save(Estudiante estudiante) {
        String sql = "INSERT INTO estudiante (acudiente_id, nombre, segundo_nombre, primer_apellido, segundo_apellido, "
                +
                "numero_registro_civil, fecha_exp, fecha_nacimiento, tipo_sangre, sexo, correo_padres, edad, " +
                "lugar_nacimiento, municipio, departamento, direccion, barrio, tipo_estudiante) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, estudiante.getAcudienteId());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getSegundoNombre());
            ps.setString(4, estudiante.getPrimerApellido());
            ps.setString(5, estudiante.getSegundoApellido());
            ps.setString(6, estudiante.getNumeroRegistroCivil());
            ps.setDate(7, estudiante.getFechaExp() != null ? Date.valueOf(estudiante.getFechaExp()) : null);
            ps.setDate(8, Date.valueOf(estudiante.getFechaNacimiento()));
            ps.setObject(9, estudiante.getTipoSangre());
            ps.setString(10, estudiante.getSexo());
            ps.setString(11, estudiante.getCorreoPadres());
            ps.setObject(12, estudiante.getEdad());
            ps.setString(13, estudiante.getLugarNacimiento());
            ps.setString(14, estudiante.getMunicipio());
            ps.setString(15, estudiante.getDepartamento());
            ps.setString(16, estudiante.getDireccion());
            ps.setString(17, estudiante.getBarrio());
            ps.setString(18, estudiante.getTipoEstudiante());
            return ps;
        }, keyHolder);

        estudiante.setEstudianteId(keyHolder.getKey().longValue());
        return estudiante;
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        String sql = "SELECT * FROM estudiante WHERE estudiante_id = ?";
        List<Estudiante> estudiantes = jdbcTemplate.query(sql, rowMapper, id);
        return estudiantes.isEmpty() ? Optional.empty() : Optional.of(estudiantes.get(0));
    }

    @Override
    public List<Estudiante> findAll() {
        String sql = "SELECT * FROM estudiante ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Estudiante> findByAcudienteId(Long acudienteId) {
        String sql = "SELECT * FROM estudiante WHERE acudiente_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, acudienteId);
    }

    @Override
    public Optional<Estudiante> findByNumeroDocumento(String numeroDocumento) {
        String sql = "SELECT * FROM estudiante WHERE numero_registro_civil = ?";
        List<Estudiante> estudiantes = jdbcTemplate.query(sql, rowMapper, numeroDocumento);
        return estudiantes.isEmpty() ? Optional.empty() : Optional.of(estudiantes.get(0));
    }

    @Override
    public void update(Estudiante estudiante) {
        String sql = "UPDATE estudiante SET nombre = ?, segundo_nombre = ?, primer_apellido = ?, segundo_apellido = ?, "
                +
                "numero_registro_civil = ?, fecha_exp = ?, fecha_nacimiento = ?, tipo_sangre = ?, sexo = ?, " +
                "correo_padres = ?, edad = ?, lugar_nacimiento = ?, municipio = ?, departamento = ?, " +
                "direccion = ?, barrio = ?, tipo_estudiante = ? WHERE estudiante_id = ?";
        jdbcTemplate.update(sql,
                estudiante.getNombre(),
                estudiante.getSegundoNombre(),
                estudiante.getPrimerApellido(),
                estudiante.getSegundoApellido(),
                estudiante.getNumeroRegistroCivil(),
                estudiante.getFechaExp() != null ? Date.valueOf(estudiante.getFechaExp()) : null,
                Date.valueOf(estudiante.getFechaNacimiento()),
                estudiante.getTipoSangre(),
                estudiante.getSexo(),
                estudiante.getCorreoPadres(),
                estudiante.getEdad(),
                estudiante.getLugarNacimiento(),
                estudiante.getMunicipio(),
                estudiante.getDepartamento(),
                estudiante.getDireccion(),
                estudiante.getBarrio(),
                estudiante.getTipoEstudiante(),
                estudiante.getEstudianteId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM estudiante WHERE estudiante_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
