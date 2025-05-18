package com.ecommerce.server.model.entity.envio;

import com.ecommerce.server.model.entity.venta.Venta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "envio")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta; // Relación con la entidad Venta (reemplaza ventasId)

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "datos_personales_id", nullable = false)
    private DatosPersonales datosPersonales;

    @Column(name = "costo_envio", nullable = false)
    private Double costoEnvio;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDate fechaEnvio;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado; // Ejemplo: "PENDIENTE", "ENVIADO", "ENTREGADO"

    @Column(name = "metodo_envio", nullable = false, length = 50)
    private String metodoEnvio; // Ejemplo: "ESTÁNDAR", "EXPRESS"

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber; // Código de seguimiento del envío

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
