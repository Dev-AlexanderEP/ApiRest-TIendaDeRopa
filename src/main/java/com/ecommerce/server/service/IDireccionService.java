package com.ecommerce.server.service;

import com.ecommerce.server.model.dto.DireccionDto;
import com.ecommerce.server.model.entity.Direccion;

import java.util.List;

public interface IDireccionService {
    List<Direccion> getDirecciones();
    Direccion getDireccion(Long id);
    Direccion save(DireccionDto direccionDto);
    void delete(Direccion direccion);
    boolean existsById(Long id);
}
