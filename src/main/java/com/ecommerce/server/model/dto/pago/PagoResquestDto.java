package com.ecommerce.server.model.dto.pago;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class PagoResquestDto {
    private Long id;
    private Long ventaId;
    private Double monto;
    private Long metodoId;
    private String estado;
}
