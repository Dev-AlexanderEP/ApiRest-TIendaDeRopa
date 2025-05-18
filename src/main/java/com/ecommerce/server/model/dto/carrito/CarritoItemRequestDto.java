package com.ecommerce.server.model.dto.carrito;

import com.ecommerce.server.model.entity.prenda.Prenda;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CarritoItemRequestDto {

    private Long id; // Opcional, para actualizaciones
    private Long carritoId; // Requerido para asociar al carrito
    private Long prendaId; // O un PrendaDto
    private Integer cantidad;
    private Double precioUnitario;

}