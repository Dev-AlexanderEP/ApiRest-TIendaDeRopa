package com.ecommerce.server.model.dto.prenda;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PrendaRequestDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long imagenId;
    private Long marcaId;
    private Long categoriaId;
    private Long proveedorId;
    private Long generoId;
    private Double precio;
    private Boolean activo;
}