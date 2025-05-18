package com.ecommerce.server.service.descuento;

import com.ecommerce.server.model.dao.descuento.DescuentoPrendaDao;
import com.ecommerce.server.model.dto.descuento.DescuentoPrendaDto;
import com.ecommerce.server.model.entity.descuento.DescuentoPrenda;

import java.util.List;

public interface IDescuentoPrendaService {
    List<DescuentoPrenda> getDescuentoPrendas();
    DescuentoPrenda getDescuentoPrenda(Long id);
    DescuentoPrenda save(DescuentoPrendaDto descuentoPrendaDto);
    void delete(DescuentoPrenda descuentoPrenda);
    boolean existsById(Long id);
}
