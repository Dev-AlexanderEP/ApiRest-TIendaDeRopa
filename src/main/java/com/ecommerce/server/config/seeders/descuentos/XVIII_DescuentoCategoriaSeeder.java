package com.ecommerce.server.config.seeders.descuentos;

import com.ecommerce.server.model.dao.descuento.DescuentoCategoriaDao;
import com.ecommerce.server.model.dao.prenda.CategoriaDao;
import com.ecommerce.server.model.entity.descuento.DescuentoCategoria;
import com.ecommerce.server.model.entity.prenda.Categoria;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class XVIII_DescuentoCategoriaSeeder {

    @Bean
    @Order(18)
    CommandLineRunner initDescuentosCategoria(DescuentoCategoriaDao descuentoDao,
                                              CategoriaDao categoriaDao) {
        return args -> {
            if (descuentoDao.count() == 0) {
                LocalDate inicio = LocalDate.now();
                LocalDate fin = inicio.plusDays(90);

                // Descuento para categoría id = 1 (ej. 10%)
                Optional<Categoria> cat1 = categoriaDao.findById(1L);
                cat1.ifPresent(c -> descuentoDao.save(
                        DescuentoCategoria.builder()
                                .categoria(c)
                                .porcentaje(10.0)     // ajusta si deseas otro porcentaje
                                .fechaInicio(inicio)
                                .fechaFin(fin)
                                .activo(true)
                                .build()
                ));

                // Descuento para categoría id = 2 (ej. 15%)
                Optional<Categoria> cat2 = categoriaDao.findById(2L);
                cat2.ifPresent(c -> descuentoDao.save(
                        DescuentoCategoria.builder()
                                .categoria(c)
                                .porcentaje(15.0)     // ajusta si deseas otro porcentaje
                                .fechaInicio(inicio)
                                .fechaFin(fin)
                                .activo(true)
                                .build()
                ));

                System.out.println("✅ Seeder DescuentoCategoria: creados descuentos para categorías 1 y 2 (fin +90 días).");
            } else {
                System.out.println("⚠️ Seeder DescuentoCategoria omitido: ya existen descuentos.");
            }
        };
    }
}
