package com.ecommerce.server.model.dto.descuento;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class DescuentoCodigoDto {
    private Long id;
    private String codigo;
    private String descripcion;
    private Double porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer usoMaximo;
    private Integer usado;
    private Boolean activo;
}
