package com.jardininfantil.web_institucional.models;

import com.jardininfantil.web_institucional.models.enums.RolUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private Long usuarioId;
    private String nombreUsuario;
    private String correo;
    private String password;
    private RolUsuario tipo_Usuario;
    private Boolean isActive;

    // Constructor vacío
    public Usuario() {}

    // Constructor con todos los parámetros
    public Usuario(
        String nombreUsuario,
        String correo,
        String password,
        RolUsuario tipo_Usuario
    ) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.password = password;
        this.tipo_Usuario = tipo_Usuario;
    }
}
