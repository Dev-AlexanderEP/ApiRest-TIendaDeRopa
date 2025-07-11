package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaDao extends CrudRepository<Categoria, Long> {
    Page<Categoria> findAll(Pageable pageable);
}
