package com.ecommerce.server.config.seeders.envio;

import com.ecommerce.server.model.dao.envio.DatosPersonalesDao; // ajusta el package si difiere
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class XVI_DatosPersonalesSeeder {

    @Bean
    @Order(16)
    CommandLineRunner initDatosPersonales(DatosPersonalesDao datosDao) {
        return args -> {
            if (datosDao.count() == 0) {
                datosDao.save(DatosPersonales.builder()
                        .nombres("Alexander")
                        .apellidos("Estrada")
                        .usuarioId(1L) // si corresponde; cámbialo o quítalo si no lo usas
                        .dni("12345678")
                        .departamento("Lima")
                        .provincia("Lima")
                        .distrito("Miraflores")
                        .calle("Av. José Pardo 123")
                        .detalle("Dpto 402, referencia: cerca al parque Kennedy")
                        .telefono("+51 999 888 777")
                        .email("alexander.estrada@example.com")
                        .build());

                System.out.println("✅ Seeder ejecutado: DatosPersonales insertado (Alexander Estrada).");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen datos personales en la base de datos.");
            }
        };
    }
}
