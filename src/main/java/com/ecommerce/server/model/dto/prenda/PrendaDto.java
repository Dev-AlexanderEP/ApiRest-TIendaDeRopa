package com.ecommerce.server.model.dto.prenda;

import com.ecommerce.server.model.entity.prenda.Imagen;
import com.ecommerce.server.model.entity.prenda.PrendaTalla;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private MarcaDto marca;
    private CategoriaDto categoria;
    private ProveedorDto proveedor;
    private GeneroDto genero;
    private Double precio;
    private Boolean activo;
    private LocalDateTime createdAt;
    private List<PrendaTalla> tallas;
}