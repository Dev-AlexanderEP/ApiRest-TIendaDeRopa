package com.ecommerce.server.controller.descuento;

import com.ecommerce.server.model.dto.descuento.DescuentoCategoriaDto;
import com.ecommerce.server.model.dto.descuento.DescuentoCategoriaRequestDto;
import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import com.ecommerce.server.model.entity.descuento.DescuentoCategoria;
import com.ecommerce.server.model.entity.prenda.Categoria;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.descuento.IDescuentoCategoriaService;
import com.ecommerce.server.service.prenda.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:4200",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe",
        "*"
})
@RestController
@RequestMapping("/api/v1")
public class DescuentoCategoriaController {


    @Autowired
    private IDescuentoCategoriaService descuentoCategoriaService;

    @Autowired
    private ICategoriaService categoriaService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/descuento-categorias")
    public ResponseEntity<?> showAll() {
        List<DescuentoCategoria> getList = descuentoCategoriaService.getDescuentoCategorias();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/descuento-categoria/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        DescuentoCategoria descuentoCategoria = descuentoCategoriaService.getDescuentoCategoria(id);
        if (descuentoCategoria == null) {
            return msg.NoGetId(); // Devuelve 404 con "El Registro que intenta buscar, NO EXISTE"
        }

        // Obtener la categoría solo si descuentoCategoria no es null
        Categoria categoria = categoriaService.getCategoria(descuentoCategoria.getCategoria().getId());
        if (categoria == null) {
            return msg.NoGetId(); // Manejar el caso donde la categoría no existe (opcional)
        }

        return msg.Get(DescuentoCategoriaDto.builder()
                .id(descuentoCategoria.getId())
                .categoriaDto(CategoriaDto.builder()
                        .id(categoria.getId())
                        .nomCategoria(categoria.getNomCategoria())
                        .build())
                .porcentaje(descuentoCategoria.getPorcentaje())
                .fechaInicio(descuentoCategoria.getFechaInicio())
                .fechaFin(descuentoCategoria.getFechaFin())
                .activo(descuentoCategoria.getActivo())
                .build());
    }

    @PostMapping("/descuento-categoria")
    public ResponseEntity<?> create(@RequestBody DescuentoCategoriaRequestDto descuentoCategoriaRequestDto) {
        DescuentoCategoria descuentoCategoriaSave = null;
        try {
            descuentoCategoriaSave = descuentoCategoriaService.save(descuentoCategoriaRequestDto);
            Categoria categoria = descuentoCategoriaSave.getCategoria();
            return msg.Post(DescuentoCategoriaDto.builder()
                    .id(descuentoCategoriaSave.getId())
                    .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                    .porcentaje(descuentoCategoriaSave.getPorcentaje())
                    .fechaInicio(descuentoCategoriaSave.getFechaInicio())
                    .fechaFin(descuentoCategoriaSave.getFechaFin())
                    .activo(descuentoCategoriaSave.getActivo())
                    .build());
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/descuento-categoria/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DescuentoCategoriaRequestDto descuentoCategoriaRequestDto) {
        DescuentoCategoria descuentoCategoriaUpdate = null;
        try{
            if (descuentoCategoriaService.existsById(id)){
                descuentoCategoriaRequestDto.setId(id);
                descuentoCategoriaUpdate = descuentoCategoriaService.save(descuentoCategoriaRequestDto);
                Categoria categoria = descuentoCategoriaUpdate.getCategoria();
                return msg.Put(DescuentoCategoriaDto.builder()
                        .id(descuentoCategoriaUpdate.getId())
                        .categoriaDto(CategoriaDto.builder().id(categoria.getId()).nomCategoria(categoria.getNomCategoria()).build())
                        .porcentaje(descuentoCategoriaUpdate.getPorcentaje())
                        .fechaInicio(descuentoCategoriaUpdate.getFechaInicio())
                        .fechaFin(descuentoCategoriaUpdate.getFechaFin())
                        .activo(descuentoCategoriaUpdate.getActivo())
                        .build());
            }else{
                return msg.NoPut();
            }
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/descuento-categoria/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            DescuentoCategoria descuentoCategoriaDelete = descuentoCategoriaService.getDescuentoCategoria(id);
            descuentoCategoriaService.deleteDescuentoCategoria(descuentoCategoriaDelete);
            return msg.Delete(descuentoCategoriaDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

}
