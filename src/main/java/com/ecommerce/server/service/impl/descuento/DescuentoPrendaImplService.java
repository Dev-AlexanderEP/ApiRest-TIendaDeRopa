package com.ecommerce.server.service.impl.descuento;

import com.ecommerce.server.model.dao.descuento.DescuentoPrendaDao;
import com.ecommerce.server.model.dto.descuento.DescuentoPrendaDto;
import com.ecommerce.server.model.entity.descuento.DescuentoPrenda;
import com.ecommerce.server.service.descuento.IDescuentoPrendaService;
import com.ecommerce.server.service.prenda.IPrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class DescuentoPrendaImplService implements IDescuentoPrendaService {
    @Autowired
    private DescuentoPrendaDao descuentoPrendaDao;

    @Autowired
    private IPrendaService prendaService;

    @Override
    public List<DescuentoPrenda> getDescuentoPrendas() {
        return (List) descuentoPrendaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public DescuentoPrenda getDescuentoPrenda(Long id) {
        return descuentoPrendaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public DescuentoPrenda save(DescuentoPrendaDto descuentoPrendaDto) {
        DescuentoPrenda descuentoPrenda = DescuentoPrenda.builder()
                .id(descuentoPrendaDto.getId())
                .prenda(prendaService.getPrenda(descuentoPrendaDto.getPrendaId()))
                .porcentaje(descuentoPrendaDto.getPorcentaje())
                .fechaInicio(descuentoPrendaDto.getFechaInicio())
                .fechaFin(descuentoPrendaDto.getFechaFin())
                .activo(descuentoPrendaDto.getActivo())
                .build();
        return descuentoPrendaDao.save(descuentoPrenda);
    }

    @Transactional
    @Override
    public void delete(DescuentoPrenda descuentoPrenda) {
        descuentoPrendaDao.delete(descuentoPrenda);

    }

    @Override
    public boolean existsById(Long id) {
        return descuentoPrendaDao.existsById(id);
    }
}
