package com.ecommerce.server.model.entity;

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
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "dni", nullable = false, length = 20, unique = true)
    private String dni;

    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    @Column(name = "distrito", nullable = false, length = 100)
    private String distrito;

    @Column(name = "calle")
    private String calle;

    @Column(name = "detalle", nullable = false, length = 255)
    private String detalle; // Dirección específica (calle, número, referencia)

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono; // Número de contacto para entregas

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}