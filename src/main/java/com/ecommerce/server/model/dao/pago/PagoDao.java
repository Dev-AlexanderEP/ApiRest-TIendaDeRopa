package com.ecommerce.server.model.dao.pago;

import com.ecommerce.server.model.entity.pago.Pago;
import org.springframework.data.repository.CrudRepository;

public interface PagoDao extends CrudRepository<Pago, Long> {
}
