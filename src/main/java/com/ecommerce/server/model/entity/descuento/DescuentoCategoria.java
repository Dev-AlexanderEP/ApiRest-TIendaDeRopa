package com.ecommerce.server.model.entity.descuento;

import com.ecommerce.server.model.entity.prenda.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "descuento_categoria")
public class DescuentoCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria; // Relación con la entidad Categoria

    @Column(name = "porcentaje", nullable = false)
    private Double porcentaje;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "activo", nullable = false)
    private Boolean activo;


}
