package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.GeneroDao;
import com.ecommerce.server.model.dto.prenda.GeneroDto;
import com.ecommerce.server.model.entity.prenda.Genero;
import com.ecommerce.server.service.prenda.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeneroImplService implements IGeneroService {
    @Autowired
    private GeneroDao generoDao;

    @Override
    public List<Genero> getGeneros() {
        return (List) generoDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genero getGenero(Long id) {
        return generoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Genero save(GeneroDto generoDto) {
        Genero genero = Genero.builder()
                .id(generoDto.getId())
                .nomGenero(generoDto.getNomGenero())
                .build();
        return generoDao.save(genero);
    }

    @Transactional
    @Override
    public void deleteGenero(Genero genero) {
        generoDao.delete(genero);
    }

    @Override
    public boolean existsById(Long id) {
        return generoDao.existsById(id);
    }
}