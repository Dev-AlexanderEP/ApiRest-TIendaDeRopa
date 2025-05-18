package com.ecommerce.server.model.dto.pago;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class MetodoPagoDto {
    private Long id;
    private String tipoPago;
}
