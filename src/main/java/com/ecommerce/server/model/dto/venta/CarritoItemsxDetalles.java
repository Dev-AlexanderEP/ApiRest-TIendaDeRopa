package com.ecommerce.server.model.dto.venta;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CarritoItemsxDetalles {
    private Long ventaId;
    private Long carritoId;
}
