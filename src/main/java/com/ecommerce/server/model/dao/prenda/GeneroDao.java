package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface GeneroDao extends CrudRepository<Genero, Long> {
    Page<Genero> findAll(Pageable pageable);
}