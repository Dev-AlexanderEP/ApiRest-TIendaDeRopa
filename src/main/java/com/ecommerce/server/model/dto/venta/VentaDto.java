package com.ecommerce.server.model.dto.venta;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
public class VentaDto {
    private Long id;
    private Long usuarioId;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<VentaDetalleDto> detalles;
}
