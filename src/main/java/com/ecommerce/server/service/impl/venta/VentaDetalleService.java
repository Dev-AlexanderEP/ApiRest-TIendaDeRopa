package com.ecommerce.server.service.impl.venta;

import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.dao.venta.VentaDetalleDao;
import com.ecommerce.server.model.dto.venta.VentaDetalleRequestDto;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.ecommerce.server.model.entity.venta.Venta;
import com.ecommerce.server.model.entity.venta.VentaDetalle;
import com.ecommerce.server.service.prenda.IPrendaService;
import com.ecommerce.server.service.venta.IVentaDetalleService;
import com.ecommerce.server.service.venta.IVentaService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaDetalleService implements IVentaDetalleService {

    @Autowired
    private VentaDetalleDao ventaDetalleDao;

    @Autowired
    private IPrendaService prendaService;
    @Autowired
    @Lazy
    private IVentaService ventaService;

    @Autowired
    private TallaDao tallaDao;

    @Override
    public List<VentaDetalle> getVentaDetalles() {
        return (List) ventaDetalleDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public VentaDetalle getVentaDetalle(Long id) {
        return ventaDetalleDao.findById(id).orElse(null);
    }

    @Transactional()
    @Override
    public VentaDetalle save(VentaDetalleRequestDto ventaDetalleRequestDto) {
        Prenda prenda  = prendaService.getPrenda(ventaDetalleRequestDto.getPrendaId());
        Talla talla = tallaDao.findById(ventaDetalleRequestDto.getTallaId()).orElse(null);
        Venta venta = ventaService.getVenta(ventaDetalleRequestDto.getVentaId());
        VentaDetalle ventaDetalle = VentaDetalle.builder()
                .id(ventaDetalleRequestDto.getId())
                .venta(venta)
                .prenda(prenda)
                .cantidad(ventaDetalleRequestDto.getCantidad())
                .talla(talla)
                .precioUnitario(ventaDetalleRequestDto.getPrecioUnitario())
                .build();
        return ventaDetalleDao.save(ventaDetalle);
    }
    @Transactional()
    @Override
    public void delete(Long id) {
        ventaDetalleDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return ventaDetalleDao.existsById(id);
    }
}
