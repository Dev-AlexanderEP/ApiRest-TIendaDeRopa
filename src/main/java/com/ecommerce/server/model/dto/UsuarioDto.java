package com.ecommerce.server.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class UsuarioDto {
    private Long id;
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private String rol;
}
