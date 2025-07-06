package com.ecommerce.server.service.impl;

import com.ecommerce.server.model.dao.prenda.ImagenDao;
import com.ecommerce.server.model.entity.prenda.Imagen;
import com.ecommerce.server.service.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImagenImplService implements IImagenService {

    @Autowired
    private ImagenDao imagenDao;

    @Override
    public List<Imagen> getImagenes() {
        return (List<Imagen>) imagenDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Imagen getImagen(Long id) {
        return imagenDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Imagen save(Imagen imagen) {
        return imagenDao.save(imagen);
    }

    @Transactional
    @Override
    public Imagen update(Imagen imagen) {
        if (!imagenDao.existsById(imagen.getId())) {
            throw new IllegalArgumentException("Imagen no encontrada con id: " + imagen.getId());
        }
        return imagenDao.save(imagen);
    }

    @Transactional
    @Override
    public void deleteImagen(Imagen imagen) {
        imagenDao.delete(imagen);
    }

    @Override
    public boolean existsById(Long id) {
        return imagenDao.existsById(id);
    }
}