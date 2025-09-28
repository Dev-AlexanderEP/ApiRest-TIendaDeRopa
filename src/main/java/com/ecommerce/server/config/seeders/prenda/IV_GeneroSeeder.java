package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.GeneroDao;
import com.ecommerce.server.model.entity.prenda.Genero;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class IV_GeneroSeeder {

    @Bean
    @Order(4)
    CommandLineRunner initGeneros(GeneroDao generoDao) {
        return args -> {
            if (generoDao.count() == 0) {
                String[] generos = new String[]{
                        "Hombre",
                        "Mujer",
                        "Niño",
                        "Niña"
                };

                for (String nombre : generos) {
                    generoDao.save(
                            Genero.builder()
                                    .nomGenero(nombre)
                                    .build()
                    );
                }

                System.out.println("✅ Seeder ejecutado: géneros insertados.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen géneros en la base de datos.");
            }
        };
    }
}
