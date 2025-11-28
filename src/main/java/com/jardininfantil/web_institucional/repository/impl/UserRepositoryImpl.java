package com.jardininfantil.web_institucional.repository.impl;

import com.jardininfantil.web_institucional.models.Usuario;
import com.jardininfantil.web_institucional.models.enums.RolUsuario;
import com.jardininfantil.web_institucional.repository.UserRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        final String SQL =
            "SELECT usuario_id, nombre_usuario, correo, password, tipo_usuario, is_active FROM usuario WHERE correo = ?";

        try {
            Usuario user = jdbcTemplate.queryForObject(
                SQL,
                (rs, rowNum) -> mapRowToUsuario(rs),
                email
            );
            return Optional.ofNullable(user);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        final String SQL = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        Integer count = jdbcTemplate.queryForObject(SQL, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void save(Usuario usuario) {
        final String SQL =
            "INSERT INTO usuario (nombre_usuario, correo, password, tipo_usuario, is_active) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            SQL,
            usuario.getNombreUsuario(),
            usuario.getCorreo(),
            usuario.getPassword(),
            usuario.getTipo_Usuario().toString(),
            usuario.getIsActive()
        );
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        final String SQL =
            "SELECT " +
            "usuario_id, nombre_usuario, correo, password, tipo_usuario, is_active " +
            "FROM usuario " +
            "WHERE usuario_id = ?";

        try {
            Usuario user = jdbcTemplate.queryForObject(
                SQL,
                (rs, rowNum) -> mapRowToUsuario(rs),
                id
            );

            return Optional.ofNullable(user);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Usuario mapRowToUsuario(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setUsuarioId(rs.getLong("usuario_id"));
        user.setNombreUsuario(rs.getString("nombre_usuario"));
        user.setCorreo(rs.getString("correo"));
        user.setPassword(rs.getString("password"));
        user.setTipo_Usuario(RolUsuario.valueOf(rs.getString("tipo_usuario")));
        user.setIsActive(rs.getBoolean("is_active"));
        return user;
    }
}
