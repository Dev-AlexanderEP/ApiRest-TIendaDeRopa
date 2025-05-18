package com.ecommerce.server.model.dao;

import com.ecommerce.server.model.entity.Resenia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReseniaDao extends CrudRepository<Resenia, Long> {
    List<Resenia> findByPrendaId(Long prendaId);
}
