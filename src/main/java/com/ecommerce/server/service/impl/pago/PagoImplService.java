package com.ecommerce.server.service.impl.pago;

import com.ecommerce.server.model.dao.pago.PagoDao;
import com.ecommerce.server.model.dto.pago.PagoResquestDto;
import com.ecommerce.server.model.entity.pago.MetodoPago;
import com.ecommerce.server.model.entity.pago.Pago;
import com.ecommerce.server.model.entity.venta.Venta;
import com.ecommerce.server.service.pago.IMetodoPagoService;
import com.ecommerce.server.service.pago.IPagoService;
import com.ecommerce.server.service.venta.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoImplService implements IPagoService {

    @Autowired
    private PagoDao pagoDao;

    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IMetodoPagoService metodoPagoService;

    @Override
    public List<Pago> getPagos() {
        return (List) pagoDao.findAll();
    }

    @Override
    public Pago getPago(Long id) {
        return pagoDao.findById(id).orElse(null);
    }

    @Override
    public Pago save(PagoResquestDto pagoResquestDto) {
        Venta venta = ventaService.getVenta(pagoResquestDto.getVentaId());
        MetodoPago metodoPago = metodoPagoService.getMetodoPago(pagoResquestDto.getMetodoId());
        Pago pago = Pago.builder()
                .id(pagoResquestDto.getId())
                .venta(venta)
                .monto(pagoResquestDto.getMonto())
                .metodoPago(metodoPago)
                .estado(pagoResquestDto.getEstado())
                .build();
        return pagoDao.save(pago);
    }

    @Override
    public void delete(Pago pago) {
        pagoDao.delete(pago);
    }

    @Override
    public boolean existsById(Long id) {
        return pagoDao.existsById(id);
    }
}
