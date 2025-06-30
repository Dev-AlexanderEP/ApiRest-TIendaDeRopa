package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.*;
import com.ecommerce.server.model.dto.prenda.*;
import com.ecommerce.server.model.entity.prenda.*;
import com.ecommerce.server.service.prenda.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrendaImplService implements IPrendaService {

    @Autowired
    private PrendaDao prendaDao;

    @Autowired
    private MarcaDao marcaDao;

    @Autowired
    private TallaDao tallaDao;

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private ProveedorDao proveedorDao;

    @Autowired
    private GeneroDao generoDao;

    @Autowired
    private IMarcaService marcaService;

    @Autowired
    private ITallaService tallaService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private ImagenDao imagenDao;

    @Override
    public List<Prenda> getPrendas() {
        return (List) prendaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Prenda getPrenda(Long id) {
        return prendaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Prenda save(Prenda prendaDto) {
        if (prendaDto.getMarca() == null || prendaDto.getMarca().getId() == null) {
            throw new IllegalArgumentException("El ID de la marca no puede ser nulo");
        }
        if (prendaDto.getCategoria() == null || prendaDto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo");
        }
        if (prendaDto.getProveedor() == null || prendaDto.getProveedor().getId() == null) {
            throw new IllegalArgumentException("El ID del proveedor no puede ser nulo");
        }
        if (prendaDto.getGenero() == null || prendaDto.getGenero().getId() == null) {
            throw new IllegalArgumentException("El ID del género no puede ser nulo");
        }

        Marca marca = marcaService.getMarca(prendaDto.getMarca().getId());
        if (marca == null) {
            throw new IllegalArgumentException("Marca con ID " + prendaDto.getMarca().getId() + " no encontrada");
        }

        Categoria categoria = categoriaService.getCategoria(prendaDto.getCategoria().getId());
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría con ID " + prendaDto.getCategoria().getId() + " no encontrada");
        }

        Proveedor proveedor = proveedorService.getProveedor(prendaDto.getProveedor().getId());
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor con ID " + prendaDto.getProveedor().getId() + " no encontrado");
        }

        Genero genero = generoService.getGenero(prendaDto.getGenero().getId());
        if (genero == null) {
            throw new IllegalArgumentException("Género con ID " + prendaDto.getGenero().getId() + " no encontrado");
        }

        Imagen imagen = null;
        if (prendaDto.getImagen() != null && prendaDto.getImagen().getId() != null) {
            imagen = imagenDao.findById(prendaDto.getImagen().getId()).orElse(null);
        }

        Prenda prenda = Prenda.builder()
                .id(prendaDto.getId())
                .nombre(prendaDto.getNombre())
                .descripcion(prendaDto.getDescripcion())
                .imagen(imagen)
                .marca(marca)
                .categoria(categoria)
                .proveedor(proveedor)
                .genero(genero)
                .precio(prendaDto.getPrecio())
                .activo(prendaDto.getActivo())
                .createdAt(prendaDto.getCreatedAt() != null ? prendaDto.getCreatedAt() : LocalDateTime.now())
                .build();

        // Asignar tallas si existen
        if (prendaDto.getTallas() != null) {
            prendaDto.getTallas().forEach(prendaTalla -> prendaTalla.setPrenda(prenda));
            prenda.setTallas(prendaDto.getTallas());
        }

        return prendaDao.save(prenda);
    }

    @Transactional
    @Override
    public void deletePrenda(Prenda prenda) {
        prendaDao.delete(prenda);
    }

    @Override
    public boolean existsById(Long id) {
        return prendaDao.existsById(id);
    }
}