package com.ecommerce.server.service.descuento;

import com.ecommerce.server.model.dto.descuento.DescuentoCategoriaRequestDto;
import com.ecommerce.server.model.entity.descuento.DescuentoCategoria;

import java.util.List;

public interface IDescuentoCategoriaService {
    List<DescuentoCategoria> getDescuentoCategorias();
    DescuentoCategoria getDescuentoCategoria(Long id);
    DescuentoCategoria save(DescuentoCategoriaRequestDto descuentoCategoriaRequestDto);
    void deleteDescuentoCategoria(DescuentoCategoria descuentoCategoria);
    boolean existsById(Long id);
}
