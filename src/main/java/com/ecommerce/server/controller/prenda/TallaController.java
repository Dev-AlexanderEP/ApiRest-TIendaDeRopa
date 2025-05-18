package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dto.prenda.TallaDto;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.ITallaService;
import org.bouncycastle.oer.its.etsi102941.TlmEntry;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class TallaController {

    @Autowired
    private ITallaService tallaService;

    private Mensajes msg = new Mensajes();


    @GetMapping("/tallas")
    public ResponseEntity<?> showAll() {
        List<Talla> getList = tallaService.getTallas();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/talla/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Talla talla = tallaService.getTalla(id);
        if (talla == null) {
            return msg.NoGet();
        }
        return msg.Get(TallaDto.builder()
                .id(talla.getId())
                .nomTalla(talla.getNomTalla())
                .build());
    }

    @PostMapping("/talla")
    public ResponseEntity<?> create(@RequestBody TallaDto tallaDto) {
        Talla tallaSave = null;
        try {
            tallaSave = tallaService.save(tallaDto);
            return msg.Post(TallaDto.builder()
                    .id(tallaSave.getId())
                    .nomTalla(tallaSave.getNomTalla())
                    .build());
        }catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/talla/{id}")
    public ResponseEntity<?> update(@RequestBody TallaDto tallaDto, @PathVariable Long id) {
        Talla tallaUpdate = null;
        try {
            if (tallaService.existsById(id)){
                tallaDto.setId(id);
                tallaUpdate = tallaService.save(tallaDto);
                return msg.Put(TallaDto.builder()
                        .id(tallaUpdate.getId())
                        .nomTalla(tallaUpdate.getNomTalla())
                        .build());
            }else{
                return msg.NoPut();
            }
        }catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/talla/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Talla tallaDelete = tallaService.getTalla(id);
            tallaService.deleteTalla(tallaDelete);
            return msg.Delete(tallaDelete);
        }catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
}
