package com.ecommerce.server.config.seeders.pago;

import com.ecommerce.server.model.dao.pago.PagoDao;
import com.ecommerce.server.model.dao.pago.MetodoPagoDao;
import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.entity.pago.MetodoPago;
import com.ecommerce.server.model.entity.pago.Pago;
import com.ecommerce.server.model.entity.venta.Venta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Optional;

@Configuration
public class XV_PagoSeeder {

    @Bean
    @Order(15)
    CommandLineRunner initPago(PagoDao pagoDao,
                               VentaDao ventaDao,
                               MetodoPagoDao metodoPagoDao) {
        return args -> {
            if (pagoDao.count() == 0) {
                // 1) Buscar venta #1 y método de pago #1
                Optional<Venta> ventaOpt = ventaDao.findById(1L);
                Optional<MetodoPago> metodoOpt = metodoPagoDao.findById(1L);

                if (ventaOpt.isPresent() && metodoOpt.isPresent()) {
                    Venta venta = ventaOpt.get();
                    MetodoPago metodo = metodoOpt.get();

                    // 2) Crear pago (ejemplo: monto fijo; cambia si deseas calcularlo de los detalles)
                    Pago pago = Pago.builder()
                            .venta(venta)
                            .monto(venta.getId() != null ? 299.90 : 0.0) // coloca el monto real si ya lo tienes
                            .metodoPago(metodo)
                            .estado("PAGADO")
                            .build();

                    pagoDao.save(pago);

                    // 3) Actualizar estado de la venta relacionada
                    venta.setEstado("PAGADO");
                    ventaDao.save(venta);

                    System.out.println("✅ Seeder ejecutado: pago creado y venta(1) marcada como PAGADO.");
                } else {
                    System.out.println("⚠️ No se pudo crear el pago: faltan Venta(id=1) o MetodoPago(id=1).");
                }
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen pagos en la base de datos.");
            }
        };
    }
}
