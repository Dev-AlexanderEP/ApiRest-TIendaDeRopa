package com.ecommerce.server.model.dao.carrito;

import com.ecommerce.server.model.entity.carrito.CarritoItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarritoItemDao extends CrudRepository<CarritoItem, Long> {
}
