package com.ecommerce.server.model.dto.venta;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class VentaRequestDto {
    private Long id;
    private Long usuarioId;
    private String estado;
}
