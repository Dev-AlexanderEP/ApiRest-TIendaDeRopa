package com.ecommerce.server.model.dao.venta;

import com.ecommerce.server.model.entity.venta.VentaDetalle;
import org.springframework.data.repository.CrudRepository;

public interface VentaDetalleDao extends CrudRepository<VentaDetalle, Long> {
}
