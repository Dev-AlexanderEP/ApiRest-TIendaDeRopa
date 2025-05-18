package com.ecommerce.server.model.entity.descuento;

import com.ecommerce.server.model.entity.prenda.Prenda;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "descuento_prenda")
public class DescuentoPrenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "prenda_id", nullable = false)
    private Prenda prenda;

    @Column(name = "porcentaje", nullable = false)
    private Double porcentaje;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private LocalDate fechaFin;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
