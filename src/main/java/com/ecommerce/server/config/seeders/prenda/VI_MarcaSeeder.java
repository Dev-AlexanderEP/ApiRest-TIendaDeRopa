package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.MarcaDao;
import com.ecommerce.server.model.entity.prenda.Marca;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class VI_MarcaSeeder {

    @Bean
    @Order(6)
    CommandLineRunner initMarcas(MarcaDao marcaDao) {
        return args -> {
            if (marcaDao.count() == 0) {
                String[] marcas = new String[]{
                        // Nacionales reconocidas
                        "Platanitos",
                        "Emporium",
                        "Micheline & Company",
                        // Internacionales muy presentes en Perú
                        "Adidas",
                        "Nike",
                        "Puma",
                        "Reebok",
                        "Under Armour",
                        "Converse",
                        "Calvin Klein",
                        "Zara",
                        "H&M",
                };

                for (String nombre : marcas) {
                    marcaDao.save(
                            Marca.builder()
                                    .nomMarca(nombre)
                                    .build()
                    );
                }

                System.out.println("✅ Seeder ejecutado: marcas insertadas.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen marcas en la base de datos.");
            }
        };
    }
}
