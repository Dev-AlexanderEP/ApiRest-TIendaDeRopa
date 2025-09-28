package com.ecommerce.server.config.seeders.carrito;

import com.ecommerce.server.model.dao.carrito.CarritoDao;
import com.ecommerce.server.model.entity.carrito.Carrito;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class X_CarritoSeeder {

    @Bean
    @Order(10)
    CommandLineRunner initCarrito(CarritoDao carritoDao) {
        return args -> {
            if (carritoDao.count() == 0) {
                carritoDao.save(Carrito.builder()
                        .usuarioId(1L)          // Usuario ficticio con ID = 1
                        .estado("ACTIVO")       // Estado inicial
                        .build());

                System.out.println("✅ Seeder ejecutado: carrito inicial creado.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen carritos en la base de datos.");
            }
        };
    }
}
