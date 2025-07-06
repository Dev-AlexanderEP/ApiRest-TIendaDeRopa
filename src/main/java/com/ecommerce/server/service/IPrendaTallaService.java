// src/main/java/com/ecommerce/server/service/IPrendaTallaService.java
package com.ecommerce.server.service;

import com.ecommerce.server.model.dto.prenda.PrendaTallaDto;
import com.ecommerce.server.model.entity.prenda.PrendaTalla;

import java.util.List;

public interface IPrendaTallaService {
    List<PrendaTalla> getPrendaTallas();
    PrendaTalla getPrendaTalla(Long id);
    PrendaTalla save(PrendaTallaDto prendaTallaDto);
    PrendaTalla update(PrendaTallaDto prendaTallaDto);
    void deletePrendaTalla(PrendaTalla prendaTalla);
    boolean existsById(Long id);
}