package com.ecommerce.server.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class ReseniaDataClientDto {
    private Long id;
    private String nombreUsuario;
    private Integer calificacion;
    private String comentario;
    private LocalDateTime fecha;

}
