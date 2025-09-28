package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.entity.prenda.Talla;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration

public class II_TallaSeeder {

    @Bean
    @Order(2)
    CommandLineRunner initTallas(TallaDao tallaDao) {
        return args -> {
            if (tallaDao.count() == 0) {
                String[] tallas = new String[]{
                        // Tallas en letras
                        "XS", "S", "M", "L", "XL",
                        // Tallas numéricas comunes (jeans, pantalones, etc.)
                        "28", "30", "32", "34", "36", "38"
                };

                for (String nombre : tallas) {
                    tallaDao.save(
                            Talla.builder()
                                    .nomTalla(nombre)
                                    .build()
                    );
                }

                System.out.println("✅ Seeder ejecutado: tallas insertadas.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen tallas en la base de datos.");
            }
        };
    }
}
