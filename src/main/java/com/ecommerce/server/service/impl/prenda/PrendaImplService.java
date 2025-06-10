package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.*;
import com.ecommerce.server.model.dto.descuento.PrendaConDescuentoResponseDto;
import com.ecommerce.server.model.dto.prenda.*;
import com.ecommerce.server.model.entity.prenda.*;
import com.ecommerce.server.service.prenda.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private IMarcaService marcaService;

    @Autowired
    private ITallaService tallaService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IProveedorService proveedorService;
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
    public Prenda save(PrendaDto prendaDto) {
        // Validaciones adicionales (aunque el controlador ya las maneja)
        if (prendaDto.getMarcaDto() == null || prendaDto.getMarcaDto().getId() == null) {
            throw new IllegalArgumentException("El ID de la marca no puede ser nulo");
        }
        if (prendaDto.getTallaDto() == null || prendaDto.getTallaDto().getId() == null) {
            throw new IllegalArgumentException("El ID de la talla no puede ser nulo");
        }
        if (prendaDto.getCategoriaDto() == null || prendaDto.getCategoriaDto().getId() == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo");
        }
        if (prendaDto.getProveedorDto() == null || prendaDto.getProveedorDto().getId() == null) {
            throw new IllegalArgumentException("El ID del proveedor no puede ser nulo");
        }

        Marca marca = marcaService.getMarca(prendaDto.getMarcaDto().getId());
        if (marca == null) {
            throw new IllegalArgumentException("Marca con ID " + prendaDto.getMarcaDto().getId() + " no encontrada");
        }

        Categoria categoria = categoriaService.getCategoria(prendaDto.getCategoriaDto().getId());
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría con ID " + prendaDto.getCategoriaDto().getId() + " no encontrada");
        }
        Proveedor proveedor = proveedorService.getProveedor(prendaDto.getProveedorDto().getId());
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor con ID " + prendaDto.getProveedorDto().getId() + " no encontrada");
        }
        Imagen imagen = imagenDao.findById(prendaDto.getImagen().getId()).orElse(null);
        // falta poner las tallas
        Prenda prenda = Prenda.builder()
                .id(prendaDto.getId())
                .nombre(prendaDto.getNombre())
                .descripcion(prendaDto.getDescripcion())
                .imagen(imagen)
                .marca(marca)
                .categoria(categoria)
                .proveedor(proveedor)
                .precio(prendaDto.getPrecio())
                .activo(prendaDto.getActivo())
                .createdAt(prendaDto.getCreatedAt() != null ? prendaDto.getCreatedAt() : LocalDateTime.now())
                .build();
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

//    @Transactional(readOnly = true)
//    public List<PrendaConDescuentoResponseDto> obtenerPrendasConDescuentos() {
//        List<Object[]> resultados = prendaDao.findPrendasConDescuentosActivos();
//        return resultados.stream().map(result -> {
//            Prenda prenda = (Prenda) result[0];
//            Double porcentaje = (Double) result[1];
//            Boolean descuentoActivo = (Boolean) result[2];
//
//            // Manejo de posibles valores nulos en las entidades relacionadas
//            MarcaDto marcaDto = prenda.getMarca() != null
//                    ? MarcaDto.builder()
//                    .id(prenda.getMarca().getId())
//                    .nomMarca(prenda.getMarca().getNomMarca())
//                    .build()
//                    : null;
//
//            TallaDto tallaDto = prenda.getTalla() != null
//                    ? TallaDto.builder()
//                    .id(prenda.getTalla().getId())
//                    .nomTalla(prenda.getTalla().getNomTalla())
//                    .build()
//                    : null;
//
//            CategoriaDto categoriaDto = prenda.getCategoria() != null
//                    ? CategoriaDto.builder()
//                    .id(prenda.getCategoria().getId())
//                    .nomCategoria(prenda.getCategoria().getNomCategoria())
//                    .build()
//                    : null;
//
//            ProveedorDto proveedorDto = prenda.getProveedor() != null
//                    ? ProveedorDto.builder()
//                    .id(prenda.getProveedor().getId())
//                    .nomProveedor(prenda.getProveedor().getNomProveedor())
//                    .build()
//                    : null;
//
//            // Mapear Prenda a PrendaDto
//            PrendaDto prendaDto = PrendaDto.builder()
//                    .id(prenda.getId())
//                    .nombre(prenda.getNombre())
//                    .descripcion(prenda.getDescripcion())
//                    .imagenUrl(prenda.getImagenUrl())
//                    .marcaDto(marcaDto)
//                    .tallaDto(tallaDto)
//                    .categoriaDto(categoriaDto)
//                    .proveedorDto(proveedorDto)
//                    .precio(prenda.getPrecio())
//                    .stock(prenda.getStock())
//                    .activo(prenda.getActivo())
//                    .createdAt(prenda.getCreatedAt())
//                    .build();
//
//            return new PrendaConDescuentoResponseDto(
//                    prendaDto,
//                    porcentaje,
//                    descuentoActivo != null ? descuentoActivo : false
//            );
//        }).collect(Collectors.toList());
//    }
}
