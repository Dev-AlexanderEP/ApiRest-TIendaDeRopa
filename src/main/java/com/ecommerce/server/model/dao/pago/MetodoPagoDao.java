package com.ecommerce.server.model.dao.pago;

import com.ecommerce.server.model.entity.pago.MetodoPago;
import org.springframework.data.repository.CrudRepository;

public interface MetodoPagoDao extends CrudRepository<MetodoPago, Long> {
}
