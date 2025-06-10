package com.ecommerce.server.model.dto.carrito;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class CarritoRequestDto {
    private Long id;
    private Long usuarioId;
    private String estado;
}
