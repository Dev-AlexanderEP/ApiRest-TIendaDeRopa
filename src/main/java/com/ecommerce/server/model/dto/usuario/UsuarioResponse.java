package com.ecommerce.server.model.dto.usuario;

import com.ecommerce.server.model.entity.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        String rol,
        Boolean activo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getActivo(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}
