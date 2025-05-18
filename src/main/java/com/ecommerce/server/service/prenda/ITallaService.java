package com.ecommerce.server.service.prenda;

import com.ecommerce.server.model.dto.prenda.TallaDto;
import com.ecommerce.server.model.entity.prenda.Talla;

import java.util.List;

public interface ITallaService {
    List<Talla> getTallas();
    Talla getTalla(Long id);
    Talla save(TallaDto tallaDto);
    void deleteTalla(Talla talla);
    boolean existsById(Long id);


}
