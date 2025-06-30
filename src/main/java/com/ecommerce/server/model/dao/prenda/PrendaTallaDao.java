package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.PrendaTalla;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrendaTallaDao extends CrudRepository<PrendaTalla, Long> {
}