package com.ecommerce.server.model.dto.descuento;

import com.ecommerce.server.model.dto.UsuarioDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
@Data
@ToString
@Builder
public class DescuentoUsuarioRequestDto {
    private Long id;
    private Long descuentoCodigoId;
    private Long usuarioId;
    private LocalDate fechaUso;
}
