package com.ecommerce.server.model.dto.descuento;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class AplicarDescuentoRequest {
    private String codigo;
    private Long usuarioId;
}
