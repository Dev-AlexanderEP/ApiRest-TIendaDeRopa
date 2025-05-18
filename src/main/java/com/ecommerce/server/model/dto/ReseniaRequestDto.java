package com.ecommerce.server.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ReseniaRequestDto {
    private Long id;
    private Long prendaId;
    private Long usuarioId;
    private Integer calificacion;
    private String comentario;
}
