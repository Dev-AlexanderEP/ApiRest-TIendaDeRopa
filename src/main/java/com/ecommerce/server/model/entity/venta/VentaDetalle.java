package com.ecommerce.server.model.entity.venta;

import com.ecommerce.server.model.entity.prenda.Prenda;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "ventas_detalle")
public class VentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "venta_id", nullable = false)
    private Long ventaId;

    @ManyToOne()
    @JoinColumn(name = "prenda_id", nullable = false)
    private Prenda prenda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
}
