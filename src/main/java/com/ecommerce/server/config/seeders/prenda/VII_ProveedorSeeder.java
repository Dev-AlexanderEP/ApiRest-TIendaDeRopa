package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.ProveedorDao;
import com.ecommerce.server.model.entity.prenda.Proveedor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class VII_ProveedorSeeder {

    @Bean
    @Order(7)
    CommandLineRunner initProveedores(ProveedorDao proveedorDao) {
        return args -> {
            if (proveedorDao.count() == 0) {
                String[] proveedores = new String[]{
                        "Textiles del Perú",
                        "Comercial Andina",
                        "Importaciones Inca",
                        "Distribuidora Fashion SAC",
                        "Textiles Alpaca",
                        "Peru Cotton Export",
                        "Proveedora Andina",
                        "Inca Style",
                        "Moda Import",
                        "Global Textil"
                };

                for (String nombre : proveedores) {
                    proveedorDao.save(
                            Proveedor.builder()
                                    .nomProveedor(nombre)
                                    .build()
                    );
                }

                System.out.println("✅ Seeder ejecutado: proveedores insertados.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen proveedores en la base de datos.");
            }
        };
    }
}
