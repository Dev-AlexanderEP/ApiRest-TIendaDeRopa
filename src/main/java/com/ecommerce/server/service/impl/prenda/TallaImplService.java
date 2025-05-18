package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.dto.prenda.TallaDto;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.ecommerce.server.service.prenda.ITallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TallaImplService implements ITallaService {

    @Autowired
    private TallaDao tallaDao;

    @Override
    public List<Talla> getTallas() {
        return (List) tallaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Talla getTalla(Long id) {
        return tallaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Talla save(TallaDto tallaDto) {
        Talla talla = Talla.builder()
                .id(tallaDto.getId())
                .nomTalla(tallaDto.getNomTalla())
                .build();
        return tallaDao.save(talla);
    }

    @Transactional
    @Override
    public void deleteTalla(Talla talla) {
        tallaDao.delete(talla);
    }

    @Override
    public boolean existsById(Long id) {
        return tallaDao.existsById(id);
    }
}
