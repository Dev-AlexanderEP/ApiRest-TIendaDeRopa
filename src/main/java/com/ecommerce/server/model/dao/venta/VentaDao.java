package com.ecommerce.server.model.dao.venta;

import com.ecommerce.server.model.entity.venta.Venta;
import org.springframework.data.repository.CrudRepository;

public interface VentaDao extends CrudRepository<Venta, Long> {
}
