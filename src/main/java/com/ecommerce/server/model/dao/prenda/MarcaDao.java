package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MarcaDao extends CrudRepository<Marca, Long> {
    Page<Marca> findAll(Pageable pageable);
}
