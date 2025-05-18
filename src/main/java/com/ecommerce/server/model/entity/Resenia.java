package com.ecommerce.server.model.entity;


import com.ecommerce.server.model.entity.prenda.Prenda;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "resenia")
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prenda_id", nullable = false)
    private Prenda prenda;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Relación con la entidad Usuario

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion; // 1 a 5

    @Column(name = "comentario")
    private String comentario;

    @CreationTimestamp
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;
}
