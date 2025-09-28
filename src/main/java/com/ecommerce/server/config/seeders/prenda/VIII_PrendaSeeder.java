package com.ecommerce.server.config.seeders.prenda;

import com.ecommerce.server.model.dao.prenda.*;
import com.ecommerce.server.model.entity.prenda.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ecommerce.server.model.dao.prenda.ImagenDao;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(8)
@RequiredArgsConstructor
public class VIII_PrendaSeeder implements CommandLineRunner {

    private final PrendaDao prendaDao;
    private final MarcaDao marcaDao;
    private final CategoriaDao categoriaDao;
    private final ProveedorDao proveedorDao;
    private final GeneroDao generoDao;
    private final ImagenDao imagenDao;

    @Override
    @Transactional
    public void run(String... args) {
        if (prendaDao.count() == 0) {
            Marca marca = marcaDao.findById(1L).orElseThrow();
            Categoria categoria = categoriaDao.findById(1L).orElseThrow();
            Proveedor proveedor = proveedorDao.findById(1L).orElseThrow();
            Genero genero = generoDao.findById(1L).orElseThrow();
            Imagen imagen = imagenDao.findById(1L).orElseThrow(); // usa el id que SÍ existe

            Prenda prenda = Prenda.builder()
                    .nombre("Abrigo Mujer Akira Beige Topo")
                    .descripcion("Abrigo elegante para mujer, modelo Akira en color beige topo.")
                    .imagen(imagen)
                    .marca(marca)
                    .categoria(categoria)
                    .proveedor(proveedor)
                    .genero(genero)
                    .precio(299.90)
                    .activo(true)
                    .build();

            prendaDao.save(prenda);
        }
    }
}
