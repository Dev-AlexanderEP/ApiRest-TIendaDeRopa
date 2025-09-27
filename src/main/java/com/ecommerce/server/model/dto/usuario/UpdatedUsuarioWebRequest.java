package com.ecommerce.server.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatedUsuarioWebRequest(
        String nombreUsuario,

        @Email(message = "Email invalido")
        String email,

        Boolean activo,

        String contrasenia,

        String rol
) {
}
