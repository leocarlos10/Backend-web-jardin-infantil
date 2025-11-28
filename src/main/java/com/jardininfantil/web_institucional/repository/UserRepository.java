package com.jardininfantil.web_institucional.repository;

import com.jardininfantil.web_institucional.models.Usuario;
import java.util.Optional;

public interface UserRepository {
    Optional<Usuario> findByEmail(String email);
    Boolean existsByEmail(String email);
    void save(Usuario usuario);
    Optional<Usuario> findById(Long id);
}
