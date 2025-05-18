package com.ecommerce.server.service.impl.pago;

import com.ecommerce.server.model.dao.pago.MetodoPagoDao;
import com.ecommerce.server.model.dto.pago.MetodoPagoDto;
import com.ecommerce.server.model.entity.pago.MetodoPago;
import com.ecommerce.server.service.pago.IMetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoPagoImplService implements IMetodoPagoService {

    @Autowired
    private MetodoPagoDao metodoPagoDao;

    @Override
    public List<MetodoPago> getMetodoPagos() {
        return (List) metodoPagoDao.findAll();
    }

    @Override
    public MetodoPago getMetodoPago(Long id){
        return metodoPagoDao.findById(id).orElse(null);
    }

    @Override
    public MetodoPago save(MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = MetodoPago.builder()
                .id(metodoPagoDto.getId())
                .tipoPago(metodoPagoDto.getTipoPago())
                .build();
        return metodoPagoDao.save(metodoPago);
    }

    @Override
    public void delete(MetodoPago metodoPago) {
        metodoPagoDao.delete(metodoPago);
    }

    @Override
    public boolean existsById(Long id) {
        return metodoPagoDao.existsById(id);
    }
}
