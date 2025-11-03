// src/main/java/com/ecommerce/server/service/impl/envio/EnvioImplService.java
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
    import java.util.ArrayList;
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
            List<Envio> list = new ArrayList<>();
            envioDao.findAll().forEach(list::add);
            return list;
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
                    .id(envioDto.getId())
                    .venta(venta)
                    .datosPersonales(datosPersonales)
                    .costoEnvio(envioDto.getCostoEnvio())
                    .fechaEnvio(envioDto.getFechaEnvio())
                    .fechaEntrega(envioDto.getFechaEntrega())
                    .estado(envioDto.getEstado())
                    .metodoEnvio(envioDto.getMetodoEnvio())
                    .trackingNumber(envioDto.getTrackingNumber())
                    .build();

            return envioDao.save(envio);
        }

        @Override
        public void delete(Envio envio) {
            envioDao.delete(envio);
        }

        @Override
        public boolean existsById(Long id) {
            return envioDao.existsById(id);
        }

        @Override
        public List<Envio> obtenerEnviosNoEntregadosPorUsuario(Long userId) {
            return envioDao.findByUsuarioIdAndEstadoNot(userId, "ENTREGADO");
        }
    }