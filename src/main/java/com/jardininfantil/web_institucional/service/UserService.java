package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.config.security.jwt.JwtUtils;
import com.jardininfantil.web_institucional.config.security.services.UserDetailsImpl;
import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.dto.user.LoginRequest;
import com.jardininfantil.web_institucional.dto.user.LoginResponse;
import com.jardininfantil.web_institucional.dto.user.RegisterRequest;
import com.jardininfantil.web_institucional.dto.user.RegisterResponse;
import com.jardininfantil.web_institucional.dto.user.UserResponse;
import com.jardininfantil.web_institucional.models.Usuario;
import com.jardininfantil.web_institucional.models.enums.RolUsuario;
import com.jardininfantil.web_institucional.pattern.observer.EventManager;
import com.jardininfantil.web_institucional.pattern.observer.EventType;
import com.jardininfantil.web_institucional.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    public UserService(
        UserRepository userRepository,
        PasswordEncoder encoder,
        AuthenticationManager authenticationManager,
        JwtUtils jwtUtils,
        EventManager eventManager
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.eventManager = eventManager;
    }

    UserRepository userRepository;
    PasswordEncoder encoder;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    private final EventManager eventManager;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Email already registered"
            );
        }

        String hashedPassword = encoder.encode(request.getPassword());

        Usuario user = new Usuario();
        user.setNombreUsuario(request.getNombre());
        user.setCorreo(request.getEmail());
        user.setPassword(hashedPassword);
        user.setIsActive(true);
        user.setTipo_Usuario(RolUsuario.USUARIO);

        try {
            userRepository.save(user);
            eventManager.notify(EventType.REGISTRO_CREADO.getValue(), user);
        } catch (Exception e) {
            throw new RuntimeException(
                "Error al guardar usuario: " + e.getMessage()
            );
        }

        RegisterResponse registerUserResponse = RegisterResponse.builder()
            .name(user.getNombreUsuario())
            .email(user.getCorreo())
            .build();

        return registerUserResponse;
    }

    @Transactional
    public Response<Object> login(
        LoginRequest request,
        HttpServletResponse response
    ) {
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() ->
                new RuntimeException("User not found. Please register first")
            );

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails =
            (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        LoginResponse loginResponse = LoginResponse.builder()
            .nombre(userDetails.getUsername())
            .email(userDetails.getEmail())
            .roles(roles)
            .accessToken(jwt)
            .tokenType("Bearer")
            .build();

        return Response.builder()
            .responseCode(200)
            .responseMessage("SUCCESS")
            .data(loginResponse)
            .build();
    }

    // GET USER THAT CURRENTLY LOGIN
    @Transactional
    public Response<Object> getUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails =
            (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        Usuario user = userRepository
            .findById(userId)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email not found !"
                )
            );

        UserResponse userResponse = UserResponse.builder()
            .id(user.getUsuarioId())
            .username(user.getNombreUsuario())
            .email(user.getCorreo())
            .isActive(user.getIsActive())
            .roles(List.of(user.getTipo_Usuario().toString()))
            .build();

        return Response.builder()
            .responseCode(200)
            .responseMessage("SUCCESS")
            .data(userResponse)
            .build();
    }
}
