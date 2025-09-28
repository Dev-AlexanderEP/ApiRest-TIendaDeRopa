package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.CategoriaDao;
import com.ecommerce.server.model.entity.prenda.Categoria;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class III_CategoriaSeeder {

    @Bean
    @Order(3)
    CommandLineRunner initCategorias(CategoriaDao categoriaDao) {
            return args -> {
                // Categorías según tu estructura de carpetas (sin "uploads")
                if (categoriaDao.count() == 0) {

                    String[] categorias = new String[]{
                            "Abrigos-blazer",
                            "Blusas",
                            "Calcetines-y-ropa-interior",
                            "Casacas",
                            "Chalecos",
                            "Chompas",
                            "Faldas",
                            "Jeans",
                            "Joggers-y-buzos",
                            "Overol-y-enterizos",
                            "Pantalones",
                            "Poleras",
                            "Polos",
                            "Ropa-deportiva",
                            "Shorts",
                            "Vestidos"
                    };

                    for (String nombre : categorias) {
                        categoriaDao.save(
                                Categoria.builder()
                                        .nomCategoria(nombre)
                                        .build()
                        );
                    }
                    System.out.println("✅ Seeder ejecutado: géneros insertados.");
                }else{
                    System.out.println("⚠️ Seeder omitido: ya existen géneros en la base de datos.");
                }
            };
    }
}
