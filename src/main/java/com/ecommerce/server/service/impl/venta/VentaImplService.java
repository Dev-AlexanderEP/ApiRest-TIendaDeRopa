package com.ecommerce.server.service.impl.venta;

import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.dto.venta.VentaDetalleDto;
import com.ecommerce.server.model.dto.venta.VentaDetalleRequestDto;
import com.ecommerce.server.model.dto.venta.VentaDto;
import com.ecommerce.server.model.dto.venta.VentaRequestDto;
import com.ecommerce.server.model.entity.carrito.Carrito;
import com.ecommerce.server.model.entity.carrito.CarritoItem;
import com.ecommerce.server.model.entity.venta.Venta;
import com.ecommerce.server.model.entity.venta.VentaDetalle;
import com.ecommerce.server.service.carrito.ICarritoService;
import com.ecommerce.server.service.venta.IVentaDetalleService;
import com.ecommerce.server.service.venta.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

@Service
public class VentaImplService implements IVentaService {

    @Autowired
    private VentaDao ventaDao;

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    @Lazy
    private IVentaDetalleService ventaDetalleService;

    @Override
    public List<Venta> getVentas() {
        return (List) ventaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Venta getVenta(Long id) {
        return ventaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public VentaDto save(VentaRequestDto ventaRequestDto) {
        String estadoDefault = "PENDIENTE";
        if (ventaRequestDto.getEstado() != null) {
            estadoDefault = ventaRequestDto.getEstado();
        }

        // Obtener la venta existente si se está actualizando (PUT)
        Venta existingVenta = null;
        if (ventaRequestDto.getId() != null) {
            existingVenta = getVenta(ventaRequestDto.getId());
        }

        // Mapear VentaRequestDto a la entidad Venta
        Venta venta = Venta.builder()
                .id(ventaRequestDto.getId()) // Si es null, se generará automáticamente
                .usuarioId(ventaRequestDto.getUsuarioId())
                .fechaCreacion(existingVenta != null ? existingVenta.getFechaCreacion() : null) // Mantener la fecha original si es un PUT
                .estado(estadoDefault)
                .detalles(existingVenta != null ? existingVenta.getDetalles() : new ArrayList<>()) // Mantener los detalles existentes
                .build();

        // Guardar la venta en la base de datos
        Venta savedVenta = ventaDao.save(venta);

        // Mapear la Venta guardada a VentaDto
        List<VentaDetalleDto> ventaDetalleDtos = savedVenta.getDetalles().stream()
                .map(detalle -> VentaDetalleDto.builder()
                        .id(detalle.getId())
                        .prendaId(detalle.getPrenda()) // Incluye el objeto Prenda completo
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        VentaDto ventaDto = VentaDto.builder()
                .id(savedVenta.getId())
                .usuarioId(savedVenta.getUsuarioId())
                .fechaCreacion(savedVenta.getFechaCreacion())
                .estado(savedVenta.getEstado())
                .detalles(ventaDetalleDtos)
                .build();

        return ventaDto;
    }

    @Transactional
    @Override
    public void delete(Venta venta) {
        ventaDao.delete(venta);
    }

    @Override
    public boolean existsById(Long id) {
        return ventaDao.existsById(id);
    }


    @Transactional
    public VentaDto agregarDetallesDesdeCarrito(Long ventaId, Long carritoId) {
        // Obtener la venta
        Venta venta = ventaDao.findById(ventaId)
                .orElseThrow(() -> new IllegalArgumentException("Venta con ID " + ventaId + " no encontrada"));

        // Obtener el carrito y sus ítems
        Carrito carrito = carritoService.getCarrito(carritoId);
        if (carrito == null || carrito.getCarritoItems() == null) {
            throw new IllegalArgumentException("Carrito con ID " + carritoId + " no encontrado o sin ítems");
        }

        List<CarritoItem> carritoItems = carrito.getCarritoItems();
        List<VentaDetalle> detalles = new ArrayList<>();

        // Iterar sobre los CarritoItem y crear VentaDetalle
        for (CarritoItem item : carritoItems) {

            VentaDetalle detalle = VentaDetalle.builder()
                    .id(null)
                    .venta(venta)
                    .prenda(item.getPrenda())
                    .cantidad(item.getCantidad())
                    .talla(item.getTalla())
                    .precioUnitario(item.getPrecioUnitario()) // Precio al momento de la venta
                    .build();
            // Guardar el detalle y añadirlo a la lista
            VentaDetalle savedDetalle = ventaDetalleService.save(VentaDetalleRequestDto.builder()
                    .id(null)
                    .ventaId(venta.getId())
                    .prendaId(item.getPrenda().getId())
                    .cantidad(item.getCantidad())
                    .tallaId(item.getTalla().getId())
                    .precioUnitario(item.getPrecioUnitario())
                    .build());
            detalles.add(savedDetalle);
        }

        // Actualizar la lista de detalles en la venta
        venta.getDetalles().addAll(detalles);
        Venta updatedVenta = ventaDao.save(venta);

        // Mapear a VentaDto
        List<VentaDetalleDto> ventaDetalleDtos = updatedVenta.getDetalles().stream()
                .map(detalle -> VentaDetalleDto.builder()
                        .id(detalle.getId())
                        .prendaId(detalle.getPrenda())
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        return VentaDto.builder()
                .id(updatedVenta.getId())
                .usuarioId(updatedVenta.getUsuarioId())
                .fechaCreacion(updatedVenta.getFechaCreacion())
                .estado(updatedVenta.getEstado())
                .detalles(ventaDetalleDtos)
                .build();
    }
}
