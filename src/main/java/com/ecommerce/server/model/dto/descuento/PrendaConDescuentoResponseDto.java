package com.ecommerce.server.model.dto.descuento;


import com.ecommerce.server.model.dto.prenda.PrendaDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PrendaConDescuentoResponseDto {

    private PrendaDto prendaDto;
    private Double porcentajeDescuento;
    private Boolean descuentoActivo;
}