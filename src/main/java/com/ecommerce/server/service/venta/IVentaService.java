package com.ecommerce.server.service.venta;

import com.ecommerce.server.model.dto.venta.VentaDto;
import com.ecommerce.server.model.dto.venta.VentaRequestDto;
import com.ecommerce.server.model.entity.venta.Venta;

import java.util.List;

public interface IVentaService {
    List<Venta> getVentas();
    Venta getVenta(Long id);
    VentaDto save(VentaRequestDto ventaRequestDto);
    void delete(Venta venta);
    boolean existsById(Long id);
    VentaDto agregarDetallesDesdeCarrito(Long ventaId, Long carritoId);
}
