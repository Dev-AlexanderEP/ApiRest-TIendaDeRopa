package com.ecommerce.server.model.dto.prenda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class MarcaDto {
    private Long id;
    private String nomMarca;
}
