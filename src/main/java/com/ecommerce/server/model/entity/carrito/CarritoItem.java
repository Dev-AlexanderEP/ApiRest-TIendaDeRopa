package com.ecommerce.server.model.entity.carrito;

import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "carrito_item")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    @JsonIgnore
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "prenda_id")
    private Prenda prenda;

    @ManyToOne
    @JoinColumn(name = "talla_id")
    private Talla talla;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;
}