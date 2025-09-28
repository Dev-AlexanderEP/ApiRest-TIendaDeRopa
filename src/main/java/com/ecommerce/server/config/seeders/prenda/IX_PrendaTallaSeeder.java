package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.*;
import com.ecommerce.server.model.entity.prenda.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class IX_PrendaTallaSeeder {

    @Bean
    @Order(9)
    CommandLineRunner initPrendaTallas(
            PrendaDao prendaDao,
            TallaDao tallaDao,
            PrendaTallaDao prendaTallaDao
    ) {
        return args -> {
            // Stock inicial por defecto
            final int STOCK_SUPERIOR = 10;
            final int STOCK_NUMERICO = 12;

            // Conjuntos de tallas
            List<String> tallasSuperiores = Arrays.asList("XS", "S", "M", "L", "XL");
            List<String> tallasNumericas  = Arrays.asList("28", "30", "32", "34", "36", "38");

            // Categorías que usarán tallas numéricas
            Set<String> catNumericas = new HashSet<>(Arrays.asList(
                    "Jeans", "Pantalones", "Shorts"
            ));

            prendaDao.findAll().forEach(prenda -> {
                // Decide set de tallas según categoría (si no hay categoría o no coincide, usa superiores)
                String categoriaNombre = prenda.getCategoria() != null ? prenda.getCategoria().getNomCategoria() : null;
                boolean usarNumericas = categoriaNombre != null && catNumericas.contains(categoriaNombre);

                List<String> tallasObjetivo = usarNumericas ? tallasNumericas : tallasSuperiores;
                int stockInicial = usarNumericas ? STOCK_NUMERICO : STOCK_SUPERIOR;

                for (String nomTalla : tallasObjetivo) {
                    Talla talla = tallaDao.findByNomTalla(nomTalla);
                    if (talla == null) {
                        // La talla no existe aún: la omitimos para no fallar
                        continue;
                    }

                    // Evitar duplicados: solo crear si no existe combinación prenda+talla
                    PrendaTalla existente = prendaTallaDao.findByPrenda_IdAndTalla_Id(prenda.getId(), talla.getId());
                    if (existente == null) {
                        PrendaTalla pt = PrendaTalla.builder()
                                .prenda(prenda)
                                .talla(talla)
                                .stock(stockInicial)
                                .build();
                        prendaTallaDao.save(pt);
                    }
                }
            });

            System.out.println("✅ Seeder ejecutado: PrendaTalla generada para prendas existentes (sin duplicar).");
        };
    }
}
