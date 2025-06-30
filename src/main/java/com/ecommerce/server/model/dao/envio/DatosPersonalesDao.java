package com.ecommerce.server.model.dao.envio;

import com.ecommerce.server.model.entity.envio.DatosPersonales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DatosPersonalesDao extends CrudRepository<DatosPersonales,Long> {
    Page<DatosPersonales> findAll(Pageable pageable);
}
