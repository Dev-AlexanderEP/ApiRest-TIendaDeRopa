package com.ecommerce.server.model.dto.descuento;

import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class DescuentoCategoriaRequestDto {
    private Long id;
    private Long categoriaId;
    private Double porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;
}
