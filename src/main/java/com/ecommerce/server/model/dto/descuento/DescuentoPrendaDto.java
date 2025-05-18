package com.ecommerce.server.model.dto.descuento;

import com.ecommerce.server.model.dto.prenda.PrendaDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class DescuentoPrendaDto {
    private Long id;
    private Long prendaId;
    private Double porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;

}
