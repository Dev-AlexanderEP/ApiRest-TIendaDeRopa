package com.ecommerce.server.service.venta;

import com.ecommerce.server.model.dto.venta.VentaDetalleRequestDto;
import com.ecommerce.server.model.entity.venta.VentaDetalle;

import java.util.List;

public interface IVentaDetalleService {
    List<VentaDetalle> getVentaDetalles();
    VentaDetalle getVentaDetalle(Long id);
    VentaDetalle save(VentaDetalleRequestDto ventaDetalleRequestDto);
    void delete(Long id);
    boolean existsById(Long id);
}
