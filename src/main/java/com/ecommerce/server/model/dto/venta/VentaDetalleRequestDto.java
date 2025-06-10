package com.ecommerce.server.model.dto.venta;

import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.venta.Venta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class VentaDetalleRequestDto {
    private Long id;
    private Long ventaId;
    private Long prendaId;
    private Long tallaId;
    private Integer cantidad;
    private Double precioUnitario;
}
