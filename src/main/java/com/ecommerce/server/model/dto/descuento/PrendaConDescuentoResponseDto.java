package com.ecommerce.server.model.dto.descuento;



import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrendaConDescuentoResponseDto {
    private Long id;
    private String nombre;
    private Double precio;
    private String imagenPrincipal;
    private String imagenHover;
    private String marca;
    private Double descuentoAplicado;
    private String tipoDescuento;
}