package com.ecommerce.server.model.dto.prenda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class TallaDto {
    private Long id;
    private String nomTalla;
}
