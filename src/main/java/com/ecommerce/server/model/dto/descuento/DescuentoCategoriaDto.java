package com.ecommerce.server.model.dto.descuento;

import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class DescuentoCategoriaDto {
    private Long id;
    private CategoriaDto categoriaDto;
    private Double porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;
}
