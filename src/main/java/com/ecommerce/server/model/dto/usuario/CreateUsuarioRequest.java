package com.ecommerce.server.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUsuarioRequest(
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String nombreUsuario,
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "Email invalido")
        String email,
        @NotBlank(message = "La contrasenia no puede estar vacío")
        String contrasenia,
        String rol
) {
}
