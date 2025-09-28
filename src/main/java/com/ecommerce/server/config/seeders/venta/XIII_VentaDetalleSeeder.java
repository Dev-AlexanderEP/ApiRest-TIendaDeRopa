package com.ecommerce.server.config.seeders.venta;

import com.ecommerce.server.model.dao.prenda.PrendaDao;
import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.dao.venta.VentaDetalleDao;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.ecommerce.server.model.entity.venta.Venta;
import com.ecommerce.server.model.entity.venta.VentaDetalle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Optional;

@Configuration
public class XIII_VentaDetalleSeeder {

    @Bean
    @Order(13)
    CommandLineRunner initVentaDetalle(VentaDetalleDao ventaDetalleDao,
                                       VentaDao ventaDao,
                                       PrendaDao prendaDao,
                                       TallaDao tallaDao) {
        return args -> {
            if (ventaDetalleDao.count() == 0) {
                Optional<Venta> ventaOpt = ventaDao.findById(1L);       // usa la venta #1
                Optional<Prenda> prendaOpt = prendaDao.findById(1L);    // prenda #1
                Talla talla = tallaDao.findByNomTalla("M");             // talla "M" (ajústala si quieres)

                if (ventaOpt.isPresent() && prendaOpt.isPresent() && talla != null) {
                    Prenda prenda = prendaOpt.get();

                    VentaDetalle detalle = VentaDetalle.builder()
                            .venta(ventaOpt.get())
                            .prenda(prenda)
                            .talla(talla)
                            .cantidad(1)
                            .precioUnitario(prenda.getPrecio()) // toma el precio actual de la prenda
                            .build();

                    ventaDetalleDao.save(detalle);
                    System.out.println("✅ Seeder ejecutado: VentaDetalle creado (venta=1, prenda=1, talla=M).");
                } else {
                    System.out.println("⚠️ No se pudo crear VentaDetalle: faltan venta(1), prenda(1) o talla 'M'.");
                }
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen ventas_detalle en la base de datos.");
            }
        };
    }
}
