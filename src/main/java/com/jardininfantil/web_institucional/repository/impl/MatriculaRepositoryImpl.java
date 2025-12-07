package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.Matricula;
import com.jardininfantil.web_institucional.models.enums.EstadoMatricula;
import com.jardininfantil.web_institucional.repository.MatriculaRepository;
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
public class MatriculaRepositoryImpl implements MatriculaRepository {

    private final JdbcTemplate jdbcTemplate;

    public MatriculaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Matricula> rowMapper = (rs, rowNum) -> {
        Matricula matricula = new Matricula();
        matricula.setIdMatricula(rs.getLong("id_matricula"));
        matricula.setEstudianteId(rs.getLong("estudiante_id"));
        matricula.setFecha(rs.getDate("fecha").toLocalDate());
        matricula.setGrado(rs.getString("grado"));
        matricula.setValorTotal(rs.getBigDecimal("valor_total"));
        matricula.setContratoFirmado(rs.getBigDecimal("contrato_firmado"));
        matricula.setEstadoMatricula(EstadoMatricula.valueOf(rs.getString("estado_matricula")));
        matricula.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        matricula.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return matricula;
    };

    @Override
    public Matricula save(Matricula matricula) {
        String sql = "INSERT INTO matricula (estudiante_id, fecha, grado, valor_total, contrato_firmado, estado_matricula) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, matricula.getEstudianteId());
            ps.setDate(2, Date.valueOf(matricula.getFecha()));
            ps.setString(3, matricula.getGrado());
            ps.setBigDecimal(4, matricula.getValorTotal());
            ps.setBigDecimal(5, matricula.getContratoFirmado());
            ps.setString(6, matricula.getEstadoMatricula().name());
            return ps;
        }, keyHolder);

        matricula.setIdMatricula(keyHolder.getKey().longValue());
        return matricula;
    }

    @Override
    public Optional<Matricula> findById(Long id) {
        String sql = "SELECT * FROM matricula WHERE id_matricula = ?";
        List<Matricula> matriculas = jdbcTemplate.query(sql, rowMapper, id);
        return matriculas.isEmpty() ? Optional.empty() : Optional.of(matriculas.get(0));
    }

    @Override
    public List<Matricula> findAll() {
        String sql = "SELECT * FROM matricula ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Matricula> findByEstudianteId(Long estudianteId) {
        String sql = "SELECT * FROM matricula WHERE estudiante_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, estudianteId);
    }

    @Override
    public List<Matricula> findByEstadoMatricula(String estado) {
        String sql = "SELECT * FROM matricula WHERE estado_matricula = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, estado);
    }

    @Override
    public List<Matricula> verMisMatriculas(Long estudianteId) {
        String sql = "SELECT * FROM matricula WHERE estudiante_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper ,estudianteId );
    }
    
    @Override
    public void update(Matricula matricula) {
        String sql = "UPDATE matricula SET fecha = ?, grado = ?, valor_total = ?, contrato_firmado = ?, estado_matricula = ? WHERE id_matricula = ?";
        jdbcTemplate.update(sql,
                Date.valueOf(matricula.getFecha()),
                matricula.getGrado(),
                matricula.getValorTotal(),
                matricula.getContratoFirmado(),
                matricula.getEstadoMatricula().name(),
                matricula.getIdMatricula());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM matricula WHERE id_matricula = ?";
        jdbcTemplate.update(sql, id);
    }
}
