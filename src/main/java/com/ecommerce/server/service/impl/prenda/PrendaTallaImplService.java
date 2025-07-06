package com.ecommerce.server.service.impl.prenda;


import com.ecommerce.server.model.dao.prenda.PrendaTallaDao;
import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.dao.prenda.PrendaDao;
import com.ecommerce.server.model.dto.prenda.PrendaTallaDto;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.PrendaTalla;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.ecommerce.server.service.IPrendaTallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrendaTallaImplService implements IPrendaTallaService {

    @Autowired
    private PrendaTallaDao prendaTallaDao;
    @Autowired
    private PrendaDao prendaDao;
    @Autowired
    private TallaDao tallaDao;

    @Override
    public List<PrendaTalla> getPrendaTallas() {
        return (List<PrendaTalla>) prendaTallaDao.findAll();
    }

    @Override
    public PrendaTalla getPrendaTalla(Long id) {
        return prendaTallaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public PrendaTalla save(PrendaTallaDto dto) {
        Prenda prenda = prendaDao.findById(dto.getPrendaId()).orElseThrow(() -> new IllegalArgumentException("Prenda no encontrada"));
        Talla talla = tallaDao.findById(dto.getTallaId()).orElseThrow(() -> new IllegalArgumentException("Talla no encontrada"));
        PrendaTalla prendaTalla = PrendaTalla.builder()
                .prenda(prenda)
                .talla(talla)
                .stock(dto.getStock())
                .build();
        return prendaTallaDao.save(prendaTalla);
    }

    @Transactional
    @Override
    public PrendaTalla update(PrendaTallaDto dto) {
        PrendaTalla prendaTalla = prendaTallaDao.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("PrendaTalla no encontrada"));
        if (dto.getStock() != null) prendaTalla.setStock(dto.getStock());
        return prendaTallaDao.save(prendaTalla);
    }

    @Transactional
    @Override
    public void deletePrendaTalla(PrendaTalla prendaTalla) {
        prendaTallaDao.delete(prendaTalla);
    }

    @Override
    public boolean existsById(Long id) {
        return prendaTallaDao.existsById(id);
    }
}