package com.ecommerce.server.model.dto.carrito;

import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.Talla;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@Builder
public class CarritoItemDto {

    private Long id;
    private Prenda prenda; // O un PrendaDto si prefieres
    private Talla talla;
    private Integer cantidad;
    private Double precioUnitario;
}