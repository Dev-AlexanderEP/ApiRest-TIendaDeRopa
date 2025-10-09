package com.ecommerce.server.controller.descuento;

import com.ecommerce.server.model.dao.descuento.DescuentoPrendaDao;
import com.ecommerce.server.model.dto.descuento.DescuentoPrendaDto;
import com.ecommerce.server.model.entity.descuento.DescuentoPrenda;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.descuento.IDescuentoPrendaService;
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
public class DescuentoPrendaService {
    @Autowired
    private IDescuentoPrendaService descuentoPrendaService;

    private Mensajes msg = new Mensajes();


    @GetMapping("/descuento-prendas")
    public ResponseEntity<?> showAll(){
        List<DescuentoPrenda> getList = descuentoPrendaService.getDescuentoPrendas();
        if (getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/descuento-prenda/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        DescuentoPrenda descuentoPrenda = descuentoPrendaService.getDescuentoPrenda(id);
        if (descuentoPrenda == null){
            return msg.NoGetId();
        }
        return msg.Get(descuentoPrenda);
    }

    @PostMapping("/descuento-prenda")
    public ResponseEntity<?> create(@RequestBody DescuentoPrendaDto descuentoPrendaDto){
        DescuentoPrenda descuentoPrendaSave = null;
        try {
            descuentoPrendaSave = descuentoPrendaService.save(descuentoPrendaDto);
            return msg.Post(descuentoPrendaSave);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/descuento-prenda/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DescuentoPrendaDto descuentoPrendaDto){
        DescuentoPrenda descuentoPrendaUpdate = null;
        try {
            if (descuentoPrendaService.existsById(id)){
                descuentoPrendaDto.setId(id);
                descuentoPrendaUpdate = descuentoPrendaService.save(descuentoPrendaDto);
                return msg.Put(descuentoPrendaUpdate);
            }else {
                return msg.NoPut();
            }

        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/descuento-prenda/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            DescuentoPrenda descuentoPrendaDelete = descuentoPrendaService.getDescuentoPrenda(id);
            descuentoPrendaService.delete(descuentoPrendaDelete);
            return msg.Delete(descuentoPrendaDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
}

