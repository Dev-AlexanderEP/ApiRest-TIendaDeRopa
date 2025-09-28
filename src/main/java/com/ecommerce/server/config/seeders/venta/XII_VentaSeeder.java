package com.ecommerce.server.config.seeders.venta;

import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.entity.venta.Venta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class XII_VentaSeeder {

    @Bean
    @Order(12)
    CommandLineRunner initVenta(VentaDao ventaDao) {
        return args -> {
            if (ventaDao.count() == 0) {
                ventaDao.save(Venta.builder()
                        .usuarioId(1L)          // ID de usuario de ejemplo
                        .estado("PENDIENTE")    // Estado inicial
                        .build());

                System.out.println("✅ Seeder ejecutado: venta inicial creada.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen ventas en la base de datos.");
            }
        };
    }
}
