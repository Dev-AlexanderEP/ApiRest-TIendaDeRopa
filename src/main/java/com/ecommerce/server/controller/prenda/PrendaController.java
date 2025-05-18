package com.ecommerce.server.controller.prenda;

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

    private Mensajes msg = new Mensajes();

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
    @GetMapping("/prenda/{id}")
    public ResponseEntity<?> showPrendaById(@PathVariable Long id) {
        Prenda prenda = prendaService.getPrenda(id);
        Marca marca = marcaService.getMarca(prenda.getMarca().getId());
        Talla talla = tallaService.getTalla(prenda.getTalla().getId());
        Categoria categoria = categoriaService.getCategoria(prenda.getCategoria().getId());
        Proveedor proveedor = proveedorService.getProveedor(prenda.getProveedor().getId());


        if (prenda == null) {
            return msg.NoGetId();
        }
        return msg.Get(PrendaDto.builder()
                .id(prenda.getId())
                .nombre(prenda.getNombre())
                .descripcion(prenda.getDescripcion())
                .imagenUrl(prenda.getImagenUrl())
                .marcaDto(MarcaDto.builder().id(marca.getId()).nomMarca(marca.getNomMarca()).build())
                .tallaDto(TallaDto.builder().id(talla.getId()).nomTalla(talla.getNomTalla()).build())
                .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                .proveedorDto(ProveedorDto.builder().id(proveedor.getId()).nomProveedor(proveedor.getNomProveedor()).build())
                .precio(prenda.getPrecio())
                .stock(prenda.getStock())
                .activo(prenda.getActivo())
                .createdAt(prenda.getCreatedAt())
                .build());
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/prenda")
    public ResponseEntity<?> create(@RequestBody PrendaDto prendaDto) {
        Prenda prendaSave = null;
        try {
            prendaSave = prendaService.save(prendaDto);
            Marca marca = marcaService.getMarca(prendaSave.getMarca().getId());
            Talla talla = tallaService.getTalla(prendaSave.getTalla().getId());
            Categoria categoria = categoriaService.getCategoria(prendaSave.getCategoria().getId());
            Proveedor proveedor = proveedorService.getProveedor(prendaSave.getProveedor().getId());

            return msg.Post(PrendaDto.builder()
                    .id(prendaSave.getId())
                    .nombre(prendaSave.getNombre())
                    .descripcion(prendaSave.getDescripcion())
                    .imagenUrl(prendaSave.getImagenUrl())
                    .marcaDto(MarcaDto.builder().id(marca.getId()).nomMarca(marca.getNomMarca()).build())
                    .tallaDto(TallaDto.builder().id(talla.getId()).nomTalla(talla.getNomTalla()).build())
                    .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                    .proveedorDto(ProveedorDto.builder().id(proveedor.getId()).nomProveedor(proveedor.getNomProveedor()).build())
                    .precio(prendaSave.getPrecio())
                    .stock(prendaSave.getStock())
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
                Talla talla = tallaService.getTalla(prendaUpdate.getTalla().getId());
                Categoria categoria = categoriaService.getCategoria(prendaUpdate.getCategoria().getId());
                Proveedor proveedor = proveedorService.getProveedor(prendaUpdate.getProveedor().getId());

                return msg.Post(PrendaDto.builder()
                        .id(prendaUpdate.getId())
                        .nombre(prendaUpdate.getNombre())
                        .descripcion(prendaUpdate.getDescripcion())
                        .imagenUrl(prendaUpdate.getImagenUrl())
                        .marcaDto(MarcaDto.builder().id(marca.getId()).nomMarca(marca.getNomMarca()).build())
                        .tallaDto(TallaDto.builder().id(talla.getId()).nomTalla(talla.getNomTalla()).build())
                        .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                        .proveedorDto(ProveedorDto.builder().id(proveedor.getId()).nomProveedor(proveedor.getNomProveedor()).build())
                        .precio(prendaUpdate.getPrecio())
                        .stock(prendaUpdate.getStock())
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

    @GetMapping("/prendas/con-descuentos")
    public ResponseEntity<?> obtenerPrendasConDescuentos() {
        try {
            List<PrendaConDescuentoResponseDto> prendasConDescuentos = prendaService.obtenerPrendasConDescuentos();
            if (prendasConDescuentos.isEmpty()) {
                return msg.NoGet();
            }
            return msg.Get(prendasConDescuentos);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

}
