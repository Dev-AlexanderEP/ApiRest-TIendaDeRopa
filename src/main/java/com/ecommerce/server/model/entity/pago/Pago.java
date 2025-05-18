package com.ecommerce.server.model.entity.pago;

import com.ecommerce.server.model.entity.venta.Venta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @ManyToOne()
    @JoinColumn(name = "metodo_id", nullable = false, referencedColumnName = "id")
    private MetodoPago metodoPago;

    @Column(name = "estado", length = 50, nullable = false)
    private String estado; // Ejemplo: "PENDIENTE", "PAGADO"

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}