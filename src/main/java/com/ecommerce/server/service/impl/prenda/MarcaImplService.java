package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.MarcaDao;
import com.ecommerce.server.model.dto.prenda.MarcaDto;
import com.ecommerce.server.model.entity.prenda.Marca;
import com.ecommerce.server.service.prenda.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MarcaImplService implements IMarcaService {
    @Autowired
    private MarcaDao marcaDao;


    @Override
    public List<Marca> getMarcas() {
        return (List) marcaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Marca getMarca(Long id) {
        return marcaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Marca save(MarcaDto marcaDto) {
        Marca marca = Marca.builder()
                .id(marcaDto.getId())
                .nomMarca(marcaDto.getNomMarca())
                .build();
        return marcaDao.save(marca);
    }
    @Transactional
    @Override
    public void deleteMarca(Marca marca) {
        marcaDao.delete(marca);
    }

    @Override
    public boolean existsById(Long id) {
        return marcaDao.existsById(id);
    }
}
