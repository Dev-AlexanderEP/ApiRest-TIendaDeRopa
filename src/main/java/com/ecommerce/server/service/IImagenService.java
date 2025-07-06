package com.ecommerce.server.service;

import com.ecommerce.server.model.entity.prenda.Imagen;

import java.util.List;

public interface IImagenService {
    List<Imagen> getImagenes();
    Imagen getImagen(Long id);
    Imagen save(Imagen imagen);
    Imagen update(Imagen imagen);
    void deleteImagen(Imagen imagen);
    boolean existsById(Long id);
}