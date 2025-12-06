package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.Acudiente;
import com.jardininfantil.web_institucional.repository.AcudienteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class AcudienteRepositoryImpl implements AcudienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public AcudienteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Acudiente> rowMapper = (rs, rowNum) -> {
        Acudiente acudiente = new Acudiente();
        acudiente.setAcudienteId(rs.getLong("acudiente_id"));
        acudiente.setUsuarioId(rs.getLong("usuario_id"));
        acudiente.setNombre(rs.getString("nombre"));
        acudiente.setSegundoNombre(rs.getString("segundo_nombre"));
        acudiente.setApellido(rs.getString("primer_apellido"));
        acudiente.setSegundoApellido(rs.getString("segundo_apellido"));
        acudiente.setNumeroDocumento(rs.getString("cedula"));
        acudiente.setTelefono(rs.getString("telefono"));
        acudiente.setCorreo(rs.getString("correo"));
        acudiente.setDireccion(rs.getString("direccion"));
        acudiente.setOcupacion(rs.getString("ocupacion"));
        acudiente.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        acudiente.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return acudiente;
    };

    @Override
    public Acudiente save(Acudiente acudiente) {
        String sql = "INSERT INTO acudiente (usuario_id, nombre, primer_apellido, cedula, telefono, correo, direccion, ocupacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, acudiente.getUsuarioId());
            ps.setString(2, acudiente.getNombre());
            ps.setString(3, acudiente.getApellido());
            ps.setString(4, acudiente.getNumeroDocumento());
            ps.setString(5, acudiente.getTelefono());
            ps.setString(6, acudiente.getCorreo());
            ps.setString(7, acudiente.getDireccion());
            ps.setString(8, acudiente.getOcupacion());
            return ps;
        }, keyHolder);

        acudiente.setAcudienteId(keyHolder.getKey().longValue());
        return acudiente;
    }

    @Override
    public Optional<Acudiente> findById(Long id) {
        String sql = "SELECT * FROM acudiente WHERE acudiente_id = ?";
        List<Acudiente> acudientes = jdbcTemplate.query(sql, rowMapper, id);
        return acudientes.isEmpty() ? Optional.empty() : Optional.of(acudientes.get(0));
    }

    @Override
    public List<Acudiente> findAll() {
        String sql = "SELECT * FROM acudiente ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Acudiente> findByUsuarioId(Long usuarioId) {
        String sql = "SELECT * FROM acudiente WHERE usuario_id = ?";
        List<Acudiente> acudientes = jdbcTemplate.query(sql, rowMapper, usuarioId);
        return acudientes.isEmpty() ? Optional.empty() : Optional.of(acudientes.get(0));
    }

    @Override
    public Optional<Acudiente> findByNumeroDocumento(String numeroDocumento) {
        String sql = "SELECT * FROM acudiente WHERE cedula = ?";
        List<Acudiente> acudientes = jdbcTemplate.query(sql, rowMapper, numeroDocumento);
        return acudientes.isEmpty() ? Optional.empty() : Optional.of(acudientes.get(0));
    }

    @Override
    public void update(Acudiente acudiente) {
        String sql = "UPDATE acudiente SET nombre = ?, primer_apellido = ?,  cedula = ?, telefono = ?, correo = ?, direccion = ?, ocupacion = ? WHERE acudiente_id = ?";
        jdbcTemplate.update(sql,
                acudiente.getNombre(),
                acudiente.getApellido(),
                acudiente.getNumeroDocumento(),
                acudiente.getTelefono(),
                acudiente.getCorreo(),
                acudiente.getDireccion(),
                acudiente.getOcupacion(),
                acudiente.getAcudienteId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM acudiente WHERE acudiente_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
