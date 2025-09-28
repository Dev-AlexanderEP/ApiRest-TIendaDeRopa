package com.ecommerce.server.controller.venta;

import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.dto.venta.CarritoItemsxDetalles;
import com.ecommerce.server.model.dto.venta.VentaDetalleDto;
import com.ecommerce.server.model.dto.venta.VentaDto;
import com.ecommerce.server.model.dto.venta.VentaRequestDto;
import com.ecommerce.server.model.entity.venta.Venta;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.venta.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class VentaController {

    @Autowired
    private IVentaService ventaService;
    @Autowired
    private VentaDao ventaDao;

    private Mensajes msg = new Mensajes();

    @GetMapping("/venta/segunda-pendiente/{usuarioId}")
    public ResponseEntity<?> getSecondPendingVentaId(@PathVariable Long usuarioId) {
        Optional<Long> ventaId = ventaDao.findSecondPendingVentaIdByUsuarioId(usuarioId);
        if (ventaId.isPresent()) {
            return msg.Get(ventaId.get());
        } else {
            return msg.NoGet();
        }
    }
    @GetMapping("/ventas")
    public ResponseEntity<?> showAll() {
        List<Venta> ventas = ventaService.getVentas();
        if (ventas.isEmpty()) {
            return msg.NoGet();
        }
        List<VentaDto> ventaDtos = ventas.stream().map(venta -> {
            List<VentaDetalleDto> ventaDetalleDtos = venta.getDetalles().stream()
                    .map(detalle -> VentaDetalleDto.builder()
                            .id(detalle.getId())
                            .prendaId(detalle.getPrenda()) // Incluye el objeto Prenda completo
                            .cantidad(detalle.getCantidad())
                            .precioUnitario(detalle.getPrecioUnitario())
                            .build())
                    .collect(Collectors.toList());

            return VentaDto.builder()
                    .id(venta.getId())
                    .usuarioId(venta.getUsuarioId())
                    .fechaCreacion(venta.getFechaCreacion())
                    .estado(venta.getEstado())
                    .detalles(ventaDetalleDtos)
                    .build();
        }).collect(Collectors.toList());
        return msg.Get(ventaDtos);
    }
    @GetMapping("/venta/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Venta venta = ventaService.getVenta(id);
        if (venta == null) {
            return msg.NoGet();
        }

        List<VentaDetalleDto> ventaDetalleDtos = venta.getDetalles().stream()
                .map(detalle -> VentaDetalleDto.builder()
                        .id(detalle.getId())
                        .prendaId(detalle.getPrenda()) // Incluye el objeto Prenda completo
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        VentaDto ventaDto = VentaDto.builder()
                .id(venta.getId())
                .usuarioId(venta.getUsuarioId())
                .fechaCreacion(venta.getFechaCreacion())
                .estado(venta.getEstado())
                .detalles(ventaDetalleDtos)
                .build();

        return msg.Get(ventaDto);
    }

    @PostMapping("/venta")
    public ResponseEntity<?> addVenta(@RequestBody VentaRequestDto ventaRequestDto) {
        try {
            VentaDto ventaDto = ventaService.save(ventaRequestDto);
            return msg.Post(ventaDto);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/venta/{id}")
    public ResponseEntity<?> updateVenta(@PathVariable Long id, @RequestBody VentaRequestDto ventaRequestDto) {
        try {
            if (ventaService.existsById(id)) {
                ventaRequestDto.setId(id);
                VentaDto ventaDto = ventaService.save(ventaRequestDto);
                return msg.Put(ventaDto);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/venta/{id}")
    public ResponseEntity<?> deleteVenta(@PathVariable Long id){
        try{
            Venta ventaDelete = ventaService.getVenta(id);
            ventaService.delete(ventaDelete);
            return msg.Delete(ventaDelete);
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

    @PostMapping("/carritodetalle")
    public ResponseEntity<?> showCarritoDetalle(@RequestBody CarritoItemsxDetalles carritoItemsxDetalles) {
        VentaDto ventaDto = ventaService.agregarDetallesDesdeCarrito(carritoItemsxDetalles.getVentaId(), carritoItemsxDetalles.getCarritoId());
        return msg.Get(ventaDto);
    }
}
