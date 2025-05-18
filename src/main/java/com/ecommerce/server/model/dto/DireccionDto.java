package com.ecommerce.server.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class DireccionDto {
    private Long id;
    private String nombres;
    private String apellidos;
    private Long usuarioId;
    private String dni;
    private String departamento;
    private String provincia;
    private String calle;
    private String distrito;
    private String detalle; // Dirección específica (calle, número, referencia)
    private String telefono; // Número de contacto para entregas
}
