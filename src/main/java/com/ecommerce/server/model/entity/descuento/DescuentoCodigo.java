package com.ecommerce.server.model.entity.descuento;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "descuento_codigo")
public class DescuentoCodigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "descripcion", nullable = true, length = 255)
    private String descripcion;

    @Column(name = "porcentaje", nullable = false)
    private Double porcentaje; // Cambiado a Double para representar decimales

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private LocalDate fechaFin;

    @Column(name = "uso_maximo", nullable = false)
    private Integer usoMaximo;

    @Column(name = "usado", nullable = false)
    private Integer usado;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

}
