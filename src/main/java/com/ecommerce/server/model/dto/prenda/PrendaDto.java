package com.ecommerce.server.model.dto.prenda;

import com.ecommerce.server.model.entity.prenda.Imagen;
import com.ecommerce.server.model.entity.prenda.Talla;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PrendaDto {


    private Long id;
    private String nombre;
    private String descripcion;
    private Imagen imagen;
    private MarcaDto marcaDto;
    private TallaDto tallaDto;
    private CategoriaDto categoriaDto;
    private ProveedorDto proveedorDto;
    private Double precio;
    private Integer stock;
    private Boolean activo;
    private LocalDateTime createdAt;
}