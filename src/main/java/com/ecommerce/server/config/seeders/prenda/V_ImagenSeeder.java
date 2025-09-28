package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.ImagenDao;
import com.ecommerce.server.model.entity.prenda.Imagen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class V_ImagenSeeder {

    @Bean
    @Order(5)
    CommandLineRunner initImagenes(ImagenDao imagenDao) {
        return args -> {
            if (imagenDao.count() == 0) {
                imagenDao.save(
                        Imagen.builder()
                                .principal("uploads/Abrigos-blazer/Abrigo-Mujer-Akira-Beige-Topo/Abrigo-Mujer-Akira-Beige-TopoP.webp")
                                .hover("uploads/Abrigos-blazer/Abrigo-Mujer-Akira-Beige-Topo/Abrigo-Mujer-Akira-Beige-TopoH.webp")
                                .img1("uploads/Abrigos-blazer/Abrigo-Mujer-Akira-Beige-Topo/Abrigo-Mujer-Akira-Beige-TopoS.webp")
                                .img2("uploads/Abrigos-blazer/Abrigo-Mujer-Akira-Beige-Topo/Abrigo-Mujer-Akira-Beige-TopoT.webp")
                                .video("uploads/Abrigos-blazer/Abrigo-Mujer-Akira-Beige-Topo/Abrigo-Mujer-Akira-Beige-TopoV.mp4")
                                .build()
                );

                System.out.println("✅ Seeder ejecutado: primera imagen guardada.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen imágenes en la base de datos.");
            }
        };
    }
}
