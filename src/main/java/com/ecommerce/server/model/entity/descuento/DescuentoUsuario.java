package com.ecommerce.server.model.entity.descuento;


import com.ecommerce.server.model.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "descuento_usuario")
public class DescuentoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "descuento_codigo_id", nullable = false)
    private DescuentoCodigo descuentoCodigo;

    @ManyToOne()
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDate fechaUso;
}
