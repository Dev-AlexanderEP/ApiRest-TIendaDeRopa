package com.ecommerce.server.model.dao.carrito;

import com.ecommerce.server.model.entity.carrito.Carrito;
import org.springframework.data.repository.CrudRepository;

public interface CarritoDao extends CrudRepository<Carrito, Long> {
}
