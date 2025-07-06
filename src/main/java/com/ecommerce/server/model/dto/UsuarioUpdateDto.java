package com.ecommerce.server.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioUpdateDto {
    private Long id;
    private String nombreUsuario;
    private String email;
    private String rol;
    private Boolean activo;
}