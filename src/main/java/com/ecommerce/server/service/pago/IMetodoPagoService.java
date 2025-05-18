package com.ecommerce.server.service.pago;


import com.ecommerce.server.model.dto.pago.MetodoPagoDto;
import com.ecommerce.server.model.entity.pago.MetodoPago;

import java.util.List;

public interface IMetodoPagoService {
    List<MetodoPago> getMetodoPagos();
    MetodoPago getMetodoPago(Long id);
    MetodoPago save(MetodoPagoDto metodoPagoDto);
    void delete(MetodoPago metodoPago);
    boolean existsById(Long id);
}
