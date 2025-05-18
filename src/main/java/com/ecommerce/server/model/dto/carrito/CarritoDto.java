package com.ecommerce.server.model.dto.carrito;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
public class CarritoDto {

    private Long id;
    private Long usuarioId;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<CarritoItemDto> carritoItems;
}