package com.ecommerce.server.controller;

import com.ecommerce.server.model.dto.ReseniaDataClientDto;
import com.ecommerce.server.model.dto.ReseniaRequestDto;
import com.ecommerce.server.model.entity.Resenia;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.IReseniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
    "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class ReseniaController {

    @Autowired
    private IReseniaService reseniaService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/resenias")
    public ResponseEntity<?> showAll(){
        List<Resenia> getList = reseniaService.getResenias();
        if (getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @PostMapping("/resenia")
    public ResponseEntity<?> create(@RequestBody ReseniaRequestDto reseniaRequestDto){
        Resenia reseniaSave = null;
        try {
            reseniaSave = reseniaService.save(reseniaRequestDto);
            return msg.Post(ReseniaDataClientDto.builder()
                    .id(reseniaSave.getId())
                    .nombreUsuario(reseniaSave.getUsuario().getNombreUsuario())
                    .calificacion(reseniaSave.getCalificacion())
                    .comentario(reseniaSave.getComentario())
                    .fecha(reseniaSave.getFecha())
                    .build());

        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

    @PutMapping("/resenia/{id}")
    public  ResponseEntity<?> update(@PathVariable Long id,@RequestBody ReseniaRequestDto reseniaRequestDto){
        Resenia reseniaUpdate = null;
        try {
            if (reseniaService.existById(id)){
                reseniaRequestDto.setId(id);
                reseniaUpdate = reseniaService.save(reseniaRequestDto);
                return msg.Put(ReseniaDataClientDto.builder()
                        .id(reseniaUpdate.getId())
                        .nombreUsuario(reseniaUpdate.getUsuario().getNombreUsuario())
                        .calificacion(reseniaUpdate.getCalificacion())
                        .comentario(reseniaUpdate.getComentario())
                        .fecha(reseniaUpdate.getFecha())
                        .build());
            }else{
                return  msg.NoPut();
            }

        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

    @DeleteMapping("/resenia/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            Resenia reseniaDelete = reseniaService.getResenia(id);
            reseniaService.delete(reseniaDelete);
            return msg.Delete(reseniaDelete);
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

    @GetMapping("/resenias-prenda/{id}")
    public ResponseEntity<?> obtenerResenasPorPrenda(@PathVariable Long id) {
        try {
            List<ReseniaDataClientDto> resenas = reseniaService.findReseniaDtoByPrendaId(id);
            if (resenas.isEmpty()) {
                return msg.NoGet();
            }
            return msg.Get(resenas);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
}
