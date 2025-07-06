package com.ecommerce.server.service.prenda;

import com.ecommerce.server.model.dto.prenda.GeneroDto;
import com.ecommerce.server.model.entity.prenda.Genero;

import java.util.List;

public interface IGeneroService {
    List<Genero> getGeneros();
    Genero getGenero(Long id);
    Genero save(GeneroDto generoDto);
    void deleteGenero(Genero genero);
    boolean existsById(Long id);
}