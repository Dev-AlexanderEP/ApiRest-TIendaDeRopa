package com.ecommerce.server.model.entity.venta;

import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    // En VentaDetalle.java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonBackReference
    private Venta venta;

    @ManyToOne()
    @JoinColumn(name = "prenda_id", nullable = false)
    private Prenda prenda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "talla_id")
    private Talla talla;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
}
