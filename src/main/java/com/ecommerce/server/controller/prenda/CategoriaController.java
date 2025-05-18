package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import com.ecommerce.server.model.entity.prenda.Categoria;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    private Mensajes msg = new Mensajes();

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
