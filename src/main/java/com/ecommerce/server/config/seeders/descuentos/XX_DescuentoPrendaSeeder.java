package com.ecommerce.server.config.seeders.descuentos;

import com.ecommerce.server.model.dao.descuento.DescuentoPrendaDao;
import com.ecommerce.server.model.dao.prenda.PrendaDao;
import com.ecommerce.server.model.entity.descuento.DescuentoPrenda;
import com.ecommerce.server.model.entity.prenda.Prenda;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class XX_DescuentoPrendaSeeder {

    @Bean
    @Order(20)
    CommandLineRunner initDescuentoPrenda(DescuentoPrendaDao descuentoPrendaDao,
                                          PrendaDao prendaDao) {
        return args -> {
            if (descuentoPrendaDao.count() == 0) {
                Optional<Prenda> prendaOpt = prendaDao.findById(1L);
                if (prendaOpt.isPresent()) {
                    LocalDate inicio = LocalDate.now();
                    LocalDate fin = inicio.plusDays(90);

                    DescuentoPrenda dp = DescuentoPrenda.builder()
                            .prenda(prendaOpt.get())
                            .porcentaje(20.0)     // ajusta el % que desees
                            .fechaInicio(inicio)
                            .fechaFin(fin)
                            .activo(true)
                            .build();

                    descuentoPrendaDao.save(dp);
                    System.out.println("✅ DescuentoPrenda creado para prenda id=1 (fin +90 días).");
                } else {
                    System.out.println("⚠️ No se creó DescuentoPrenda: no existe Prenda id=1.");
                }
            } else {
                System.out.println("ℹ️ Seeder DescuentoPrenda omitido: ya hay registros.");
            }
        };
    }
}
