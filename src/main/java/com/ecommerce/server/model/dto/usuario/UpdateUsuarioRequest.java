package com.ecommerce.server.model.dto.usuario;

import jakarta.validation.constraints.Email;

public record UpdateUsuarioRequest(
        Long id,
        String nombreUsuario,
        @Email(message = "Email invalido")
        String email,
        Boolean activo,
        String contrasenia,
        String rol
) {
    public static UpdateUsuarioRequest from(UpdatedUsuarioWebRequest dto, Long id) {
        return new UpdateUsuarioRequest(
                id,
                dto.nombreUsuario(),
                dto.email(),
                dto.activo(),
                dto.contrasenia(),
                dto.rol()
        );
    }
}
