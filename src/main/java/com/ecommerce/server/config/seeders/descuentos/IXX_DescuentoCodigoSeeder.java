package com.ecommerce.server.config.seeders.descuentos;

import com.ecommerce.server.model.dao.descuento.DescuentoCodigoDao;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;

@Configuration
public class IXX_DescuentoCodigoSeeder {

    @Bean
    @Order(19)
    CommandLineRunner initDescuentoCodigos(DescuentoCodigoDao dao) {
        return args -> {
            if (dao.count() == 0) {
                LocalDate inicio = LocalDate.now();
                LocalDate fin = inicio.plusDays(90);

                dao.save(DescuentoCodigo.builder()
                        .codigo("DESC10").descripcion("Cupón DESC10 de 10%")
                        .porcentaje(10.0).fechaInicio(inicio).fechaFin(fin)
                        .usoMaximo(100).usado(0).activo(true).build());

                dao.save(DescuentoCodigo.builder()
                        .codigo("DESC50").descripcion("Cupón DESC50 de 50%")
                        .porcentaje(50.0).fechaInicio(inicio).fechaFin(fin)
                        .usoMaximo(100).usado(0).activo(true).build());

                System.out.println("✅ Creados DESC10 y DESC50.");
            } else {
                System.out.println("⚠️ Omitido: la tabla no está vacía.");
            }
        };
    }

}
