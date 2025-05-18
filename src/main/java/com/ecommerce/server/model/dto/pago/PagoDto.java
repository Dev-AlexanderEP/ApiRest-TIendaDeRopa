package com.ecommerce.server.model.dto.pago;

import com.ecommerce.server.model.dto.venta.VentaDto;
import com.ecommerce.server.model.entity.venta.Venta;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class PagoDto {

    private Long id;
    private Venta ventaId;
    private Double monto;
    private MetodoPagoDto metodo;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime updatedAt;
}