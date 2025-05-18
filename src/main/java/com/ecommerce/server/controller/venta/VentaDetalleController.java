package com.ecommerce.server.controller.venta;

import com.ecommerce.server.model.dto.venta.VentaDetalleDto;
import com.ecommerce.server.model.dto.venta.VentaDetalleRequestDto;
import com.ecommerce.server.model.entity.venta.VentaDetalle;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.venta.IVentaDetalleService;
import com.ecommerce.server.service.venta.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class VentaDetalleController {
    @Autowired
    private IVentaDetalleService ventaDetalleService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/venta-detalles")
    public ResponseEntity<?> showAll() {
        try {
            List<VentaDetalle> ventaDetalles = ventaDetalleService.getVentaDetalles();
            if (ventaDetalles.isEmpty()) {
                return msg.NoGet();
            }
            return msg.Get(ventaDetalles);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/venta-detalle/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            VentaDetalle ventaDetalle = ventaDetalleService.getVentaDetalle(id);
            if (ventaDetalle == null) {
                return msg.NoGet();
            }
            return msg.Get(ventaDetalle);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
    @PostMapping("/venta-detalle")
    public ResponseEntity<?> addVentaDetalle(@RequestBody VentaDetalleRequestDto ventaDetalleRequestDto) {
        try {
            VentaDetalle ventaDetalleDto = ventaDetalleService.save(ventaDetalleRequestDto);
            return msg.Post(ventaDetalleDto);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/venta-detalle/{id}")
    public ResponseEntity<?> updateVentaDetalle(@PathVariable Long id, @RequestBody VentaDetalleRequestDto ventaDetalleRequestDto) {
        try {
            if (ventaDetalleService.existsById(id)) {
                ventaDetalleRequestDto.setId(id);
                VentaDetalle ventaDetalleDto = ventaDetalleService.save(ventaDetalleRequestDto);
                return msg.Put(ventaDetalleDto);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/venta-detalle/{id}")
    public ResponseEntity<?> deleteVentaDetalle(@PathVariable Long id) {
        try {
            VentaDetalle ventaDetalleDelete = ventaDetalleService.getVentaDetalle(id);
            ventaDetalleService.delete(id);
            return msg.Delete(ventaDetalleDelete);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
}
