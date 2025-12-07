package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.Reserva;
import com.jardininfantil.web_institucional.models.enums.EstadoReserva;
import com.jardininfantil.web_institucional.repository.ReservaRepository;
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
public class ReservaRepositoryImpl implements ReservaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reserva> rowMapper = (rs, rowNum) -> {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getLong("id_reserva"));
        reserva.setEstudianteId(rs.getLong("estudiante_id"));
        reserva.setEstadoReserva(EstadoReserva.valueOf(rs.getString("estado_reserva")));
        reserva.setGradoSolicitado(rs.getString("grado_solicitado"));
        reserva.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        reserva.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return reserva;
    };

    @Override
    public Reserva save(Reserva reserva) {
        String sql = "INSERT INTO reserva_cupo (estudiante_id, estado_reserva, grado_solicitado) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, reserva.getEstudianteId());
            ps.setString(2, reserva.getEstadoReserva().name());
            ps.setString(3, reserva.getGradoSolicitado());
            return ps;
        }, keyHolder);

        reserva.setIdReserva(keyHolder.getKey().longValue());
        return reserva;
    }

    @Override
    public Optional<Reserva> findById(Long id) {
        String sql = "SELECT * FROM reserva_cupo WHERE id_reserva = ?";
        List<Reserva> reservas = jdbcTemplate.query(sql, rowMapper, id);
        return reservas.isEmpty() ? Optional.empty() : Optional.of(reservas.get(0));
    }

    @Override
    public List<Reserva> findAll() {
        String sql = "SELECT * FROM reserva_cupo ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    @Override
    public List<Reserva> verMisReservas(Long estudianteId) {
        String sql = "SELECT * FROM reserva_cupo WHERE estudiante_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper ,estudianteId );
    }

    @Override
    public List<Reserva> findByEstudianteId(Long estudianteId) {
        String sql = "SELECT * FROM reserva_cupo WHERE estudiante_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, estudianteId);
    }

    @Override
    public List<Reserva> findByEstadoReserva(String estado) {
        String sql = "SELECT * FROM reserva_cupo WHERE estado_reserva = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, estado);
    }

    @Override
    public void update(Reserva reserva) {
        String sql = "UPDATE reserva_cupo SET estado_reserva = ?, grado_solicitado = ? WHERE id_reserva = ?";
        jdbcTemplate.update(sql, reserva.getEstadoReserva().name(), reserva.getGradoSolicitado(),
                reserva.getIdReserva());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reserva_cupo WHERE id_reserva = ?";
        jdbcTemplate.update(sql, id);
    }
}
