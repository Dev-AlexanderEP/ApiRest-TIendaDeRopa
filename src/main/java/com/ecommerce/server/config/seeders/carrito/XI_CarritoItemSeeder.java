package com.ecommerce.server.config.seeders.carrito;

import com.ecommerce.server.model.dao.carrito.CarritoDao;
import com.ecommerce.server.model.dao.carrito.CarritoItemDao;
import com.ecommerce.server.model.dao.prenda.PrendaDao;
import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.entity.carrito.Carrito;
import com.ecommerce.server.model.entity.carrito.CarritoItem;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.entity.prenda.Talla;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Optional;

@Configuration
public class XI_CarritoItemSeeder {

    @Bean
    @Order(11)
    CommandLineRunner initCarritoItem(CarritoItemDao carritoItemDao,
                                      CarritoDao carritoDao,
                                      PrendaDao prendaDao,
                                      TallaDao tallaDao) {
        return args -> {
            if (carritoItemDao.count() == 0) {
                Optional<Carrito> carritoOpt = carritoDao.findById(1L);
                Optional<Prenda> prendaOpt = prendaDao.findById(1L);
                Talla tallaOpt = tallaDao.findByNomTalla("M"); // ⚡ Puedes cambiar la talla

                if (carritoOpt.isPresent() && prendaOpt.isPresent() ) {
                    CarritoItem item = CarritoItem.builder()
                            .carrito(carritoOpt.get())
                            .prenda(prendaOpt.get())
                            .talla(tallaOpt)
                            .precioUnitario(prendaOpt.get().getPrecio())
                            .cantidad(1)
                            .build();

                    carritoItemDao.save(item);
                    System.out.println("✅ Seeder ejecutado: CarritoItem creado en el carrito 1.");
                } else {
                    System.out.println("⚠️ No se pudo crear CarritoItem (faltan datos en carrito, prenda o talla).");
                }
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen CarritoItems en la base de datos.");
            }
        };
    }
}
