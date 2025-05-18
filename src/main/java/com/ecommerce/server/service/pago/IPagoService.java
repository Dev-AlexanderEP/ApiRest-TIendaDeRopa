package com.ecommerce.server.service.pago;

import com.ecommerce.server.model.dto.pago.MetodoPagoDto;
import com.ecommerce.server.model.dto.pago.PagoResquestDto;
import com.ecommerce.server.model.entity.pago.MetodoPago;
import com.ecommerce.server.model.entity.pago.Pago;

import java.util.List;

public interface IPagoService {
    List<Pago> getPagos();
    Pago getPago(Long id);
    Pago save(PagoResquestDto pagoResquestDto);
    void delete(Pago pago);
    boolean existsById(Long id);
}
