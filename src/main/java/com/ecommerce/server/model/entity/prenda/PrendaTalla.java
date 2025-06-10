// src/main/java/com/ecommerce/server/model/entity/prenda/PrendaTalla.java
package com.ecommerce.server.model.entity.prenda;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "prenda_talla")
public class PrendaTalla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prenda_id")
    @JsonBackReference
    private Prenda prenda;

    @ManyToOne
    @JoinColumn(name = "talla_id")
    private Talla talla;

    @Column(name = "stock")
    private Integer stock;
}