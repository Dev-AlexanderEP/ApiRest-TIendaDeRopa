package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dao.prenda.ImagenDao;
import com.ecommerce.server.model.dao.prenda.PrendaCustomRepository;
import com.ecommerce.server.model.dao.prenda.PrendaDao;
import com.ecommerce.server.model.dto.descuento.PrendaConDescuentoResponseDto;
import com.ecommerce.server.model.dto.prenda.*;
import com.ecommerce.server.model.entity.prenda.*;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class PrendaController {



    @Autowired
    private IPrendaService prendaService;

    @Autowired
    private IMarcaService marcaService;

    @Autowired
    private ITallaService tallaService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private PrendaDao prendaDao;

    @Autowired
    private ImagenDao imagenDao;

    private Mensajes msg = new Mensajes();


    @GetMapping("/prenda-tallas/{categoria}")
    public ResponseEntity<?> getTallasPorCategoria(@PathVariable String categoria) {
        List<String> tallas = prendaDao.findTallasByCategoriaLike(categoria);
        return msg.Get(tallas);
    }
    @GetMapping("/prenda-marcas/{categoria}")
    public ResponseEntity<?> getMarcasPorCategoria(@PathVariable String categoria) {
        List<String> marcas = prendaDao.findMarcasByCategoriaLike(categoria);
        return msg.Get(marcas);
    }
    @GetMapping("/prenda-precios/{categoria}")
    public ResponseEntity<?> getEstadisticasPreciosPorCategoria(@PathVariable String categoria) {
        Object[] stats = prendaDao.findEstadisticasPreciosByCategoriaLike(categoria);
        // stats[0]: promedio, stats[1]: mínimo, stats[2]: máximo
        return msg.Get(stats);
    }
// no sirve
    @GetMapping("/prendas-por-categoria/{categoria}")
    public ResponseEntity<?> getPrendasPorCategoria(@PathVariable String categoria) {
        List<Prenda> prendas = prendaDao.findPrendasByCategoriaLike(categoria);
        return msg.Get(prendas);
    }

    @GetMapping("/prendas/todos-descuentos/{categoria}")
    public ResponseEntity<?> getTodosDescuentosPorCategoria(@PathVariable String categoria) {
        try {
            List<Double> descuentos = prendaDao.findSoloDescuentosPorCategoria(categoria);
            return msg.Get(descuentos);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/prendas/descuentos-aplicados/{categoria}")
    public ResponseEntity<?> getPrendasConDescuentoAplicadoPorCategoria(@PathVariable String categoria) {
        try {
            List<PrendaConDescuentoResponseDto> resultados = prendaDao.findPrendasConDescuentoAplicadoPorCategoria(categoria);
            return msg.Get(resultados);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/prendas-filtradas")
    public ResponseEntity<?> filtrarPrendasDinamico(
            @RequestParam(required = false) String talla,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Double descMin,
            @RequestParam(required = false) Double descMax
    ) {
        try {
            List<PrendaConDescuentoResponseDto> prendas = prendaDao.filtrarPrendasDinamico(
                    talla, categoria, marca, precioMin, precioMax, descMin, descMax
            );
            return msg.Get(prendas);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }


    //    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @GetMapping("/prendas")
    public ResponseEntity<?> showAllPrendas() {
        List<Prenda> getList = prendaService.getPrendas();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    //    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    //falta tallas
    @GetMapping("/prenda/{id}")
    public ResponseEntity<?> showPrendaById(@PathVariable Long id) {
        Prenda prenda = prendaService.getPrenda(id);
        Marca marca = marcaService.getMarca(prenda.getMarca().getId());
        Categoria categoria = categoriaService.getCategoria(prenda.getCategoria().getId());
        Proveedor proveedor = proveedorService.getProveedor(prenda.getProveedor().getId());
        Imagen imagen = imagenDao.findById(prenda.getImagen().getId()).orElse(null);

        if (prenda == null) {
            return msg.NoGetId();
        }
        return msg.Get(prenda);
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/prenda")
    public ResponseEntity<?> create(@RequestBody PrendaDto prendaDto) {
        Prenda prendaSave = null;
        try {
            prendaSave = prendaService.save(prendaDto);
            Marca marca = marcaService.getMarca(prendaSave.getMarca().getId());
            Categoria categoria = categoriaService.getCategoria(prendaSave.getCategoria().getId());
            Proveedor proveedor = proveedorService.getProveedor(prendaSave.getProveedor().getId());
            Imagen imagen = imagenDao.findById(prendaSave.getImagen().getId()).orElse(null);

            return msg.Post(PrendaDto.builder()
                    .id(prendaSave.getId())
                    .nombre(prendaSave.getNombre())
                    .descripcion(prendaSave.getDescripcion())
                    .imagen(imagen)
                    .marcaDto(MarcaDto.builder().id(marca.getId()).nomMarca(marca.getNomMarca()).build())
                    .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                    .proveedorDto(ProveedorDto.builder().id(proveedor.getId()).nomProveedor(proveedor.getNomProveedor()).build())
                    .precio(prendaSave.getPrecio())
                    .activo(prendaSave.getActivo())
                    .createdAt(prendaSave.getCreatedAt())
                    .build());
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

//        @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PutMapping("/prenda/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PrendaDto prendaDto) {
        Prenda prendaUpdate = null;
        try {
            if (prendaService.existsById(id)) {
                prendaDto.setId(id);
                prendaUpdate = prendaService.save(prendaDto);
                Marca marca = marcaService.getMarca(prendaUpdate.getMarca().getId());
                Categoria categoria = categoriaService.getCategoria(prendaUpdate.getCategoria().getId());
                Proveedor proveedor = proveedorService.getProveedor(prendaUpdate.getProveedor().getId());
                Imagen imagen = imagenDao.findById(prendaUpdate.getImagen().getId()).orElse(null);

                return msg.Post(PrendaDto.builder()
                        .id(prendaUpdate.getId())
                        .nombre(prendaUpdate.getNombre())
                        .descripcion(prendaUpdate.getDescripcion())
                                .imagen(imagen)
                        .marcaDto(MarcaDto.builder().id(marca.getId()).nomMarca(marca.getNomMarca()).build())
                        .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                        .proveedorDto(ProveedorDto.builder().id(proveedor.getId()).nomProveedor(proveedor.getNomProveedor()).build())
                        .precio(prendaUpdate.getPrecio())
                        .activo(prendaUpdate.getActivo())
                        .createdAt(prendaUpdate.getCreatedAt())
                        .build());
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }


    //    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/prenda/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            Prenda prendaDelete = prendaService.getPrenda(id);
            if (prendaDelete != null) {
                prendaService.deletePrenda(prendaDelete);
                return msg.Delete(prendaDelete);
            }
            return msg.NoGetId();
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

//    @GetMapping("/prendas/con-descuentos")
//    public ResponseEntity<?> obtenerPrendasConDescuentos() {
//        try {
//            List<PrendaConDescuentoResponseDto> prendasConDescuentos = prendaService.obtenerPrendasConDescuentos();
//            if (prendasConDescuentos.isEmpty()) {
//                return msg.NoGet();
//            }
//            return msg.Get(prendasConDescuentos);
//        } catch (DataAccessException e) {
//            return msg.Error(e);
//        }
//    }

}
