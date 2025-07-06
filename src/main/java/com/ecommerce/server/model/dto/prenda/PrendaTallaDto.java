// src/main/java/com/ecommerce/server/model/dto/prenda/PrendaTallaDto.java
package com.ecommerce.server.model.dto.prenda;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrendaTallaDto {
    private Long id;
    private Long prendaId;
    private Long tallaId;
    private Integer stock;
}