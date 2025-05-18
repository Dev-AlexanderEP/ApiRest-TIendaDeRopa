package com.ecommerce.server.service.impl.envio;

import com.ecommerce.server.model.dao.envio.EnvioDao;
import com.ecommerce.server.model.dto.envio.EnvioDto;
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import com.ecommerce.server.model.entity.envio.Envio;
import com.ecommerce.server.model.entity.venta.Venta;
import com.ecommerce.server.service.envio.IDatosPersonalesService;
import com.ecommerce.server.service.envio.IEnvioService;
import com.ecommerce.server.service.venta.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnvioImplService implements IEnvioService {

    @Autowired
    private EnvioDao envioDao;

    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IDatosPersonalesService datosPersonalesService;

    @Override
    public List<Envio> getEnvios() {
        return (List) envioDao.findAll();
    }

    @Override
    public Envio getEnvio(Long id) {
        return envioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Envio save(EnvioDto envioDto) {
        Venta venta = ventaService.getVenta(envioDto.getVentaId());

        DatosPersonales datosPersonales = datosPersonalesService.getDatoPersonal(envioDto.getDatosPersonalesId());

        Envio envio = Envio.builder()
                .id(envioDto.getId()) // Si es null, se generará automáticamente
                .venta(venta)
                .datosPersonales(datosPersonales)
                .costoEnvio(envioDto.getCostoEnvio())
                .fechaEnvio(envioDto.getFechaEnvio())
                .fechaEntrega(envioDto.getFechaEntrega())
                .estado(envioDto.getEstado())
                .metodoEnvio(envioDto.getMetodoEnvio())
                .trackingNumber(envioDto.getTrackingNumber())
                .build();

        Envio savedEnvio = envioDao.save(envio);

        return savedEnvio;
    }

    @Override
    public void delete(Envio envio) {
        envioDao.delete(envio);
    }

    @Override
    public boolean existsById(Long id) {
        return envioDao.existsById(id);
    }

}
