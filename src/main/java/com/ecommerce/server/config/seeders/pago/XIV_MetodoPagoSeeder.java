package com.ecommerce.server.config.seeders.pago;

import com.ecommerce.server.model.dao.pago.MetodoPagoDao;
import com.ecommerce.server.model.entity.pago.MetodoPago;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class XIV_MetodoPagoSeeder {

    @Bean
    @Order(14)
    CommandLineRunner initMetodosPago(MetodoPagoDao metodoPagoDao) {
        return args -> {
            if (metodoPagoDao.count() == 0) {
                String[] metodos = new String[]{
                        "Tarjeta de Crédito",
                        "Tarjeta de Débito",
                        "Yape",
                        "Plin",
                        "Transferencia Bancaria",
                        "PayPal"
                };

                for (String metodo : metodos) {
                    metodoPagoDao.save(
                            MetodoPago.builder()
                                    .tipoPago(metodo)
                                    .build()
                    );
                }

                System.out.println("✅ Seeder ejecutado: métodos de pago insertados.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen métodos de pago en la base de datos.");
            }
        };
    }
}
