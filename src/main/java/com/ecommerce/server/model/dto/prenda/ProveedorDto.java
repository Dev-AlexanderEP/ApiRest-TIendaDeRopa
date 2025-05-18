package com.ecommerce.server.model.dto.prenda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ProveedorDto {
    private Long id;
    private String nomProveedor;
}
