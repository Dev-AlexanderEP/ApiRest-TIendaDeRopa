package com.ecommerce.server.model.dto.descuento;

import com.ecommerce.server.model.dto.UsuarioDto;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class DescuentoUsuarioDto {
    private Long id;
    private DescuentoCodigo descuentoCodigo;
    private Usuario usuario;
    private LocalDate fechaUso;
}
