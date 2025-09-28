package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dao.prenda.CategoriaDao;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import com.ecommerce.server.model.entity.prenda.Categoria;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    private Mensajes msg = new Mensajes();

    @Autowired
    private CategoriaDao categoriaDao;
    @GetMapping("/categorias/paginado")
    public ResponseEntity<?> getCategoriasPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Categoria> categorias = categoriaDao.findAll(pageable);
            if (categorias.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<Categoria> response = new PageResponseDto<>(
                    categorias.getContent(),
                    categorias.getNumber(),
                    categorias.getSize(),
                    categorias.getTotalElements(),
                    categorias.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> showAll(){
        List<Categoria> getList = categoriaService.getCategorias();
        if(getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }


    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        Categoria categoria = categoriaService.getCategoria(id);
        if(categoria == null){
            return msg.NoGet();
        }
        return msg.Get(CategoriaDto.builder()
                .id(categoria.getId())
                .nomCategoria(categoria.getNomCategoria())
                .build());
    }

    @PostMapping("/categoria")
    public ResponseEntity<?> create(@RequestBody CategoriaDto categoriaDto){
        Categoria categoriaSave = null;
        try {
            categoriaSave = categoriaService.save(categoriaDto);
            return msg.Post(CategoriaDto.builder()
                    .id(categoriaSave.getId())
                    .nomCategoria(categoriaSave.getNomCategoria())
                    .build());
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> update(@RequestBody CategoriaDto categoriaDto, @PathVariable Long id){
        Categoria categoriaUpdate = null;
        try {
            if (categoriaService.existsByID(id)){
                categoriaDto.setId(id);
                categoriaUpdate = categoriaService.save(categoriaDto);
                return msg.Put(CategoriaDto.builder()
                        .id(categoriaUpdate.getId())
                        .nomCategoria(categoriaUpdate.getNomCategoria())
                        .build());
            }else {
                return msg.NoPut();
            }
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            Categoria categoriaDelete = categoriaService.getCategoria(id);
            categoriaService.deleteCategoria(categoriaDelete);
            return  msg.Delete(categoriaDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

}
