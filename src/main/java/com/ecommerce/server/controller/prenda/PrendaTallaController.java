package com.ecommerce.server.controller.prenda;


import com.ecommerce.server.model.dao.prenda.PrendaTallaDao;
import com.ecommerce.server.model.dto.prenda.PrendaTallaDto;
import com.ecommerce.server.model.entity.prenda.PrendaTalla;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.IPrendaTallaService;
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
public class PrendaTallaController {

    @Autowired
    private IPrendaTallaService prendaTallaService;

    private Mensajes msg = new Mensajes();


    @Autowired
    private PrendaTallaDao prendaTallaDao;

    @PutMapping("/restar-uno")
    public ResponseEntity<?> restarUnoStock(@RequestParam Long prendaId, @RequestParam Long tallaId) {
        int filas = prendaTallaDao.restarUnoStock(prendaId, tallaId);
        if (filas == 0) {
            return msg.Personalizado("Stock insuficiente o combinación no encontrada.");
        }
        PrendaTalla prendaTalla = prendaTallaDao.findByPrenda_IdAndTalla_Id(prendaId, tallaId);
        return msg.Put(prendaTalla);
    }
    @PutMapping("/sumar-uno")
    public ResponseEntity<?> sumarUnoStock(@RequestParam Long prendaId, @RequestParam Long tallaId) {
        int filas = prendaTallaDao.sumarUnoStock(prendaId, tallaId);
        if (filas == 0) {
            return msg.Personalizado("Combinación prenda-talla no encontrada.");
        }
        PrendaTalla prendaTalla = prendaTallaDao.findByPrenda_IdAndTalla_Id(prendaId, tallaId);
        return msg.Put(prendaTalla);
    }

    @PutMapping("/sumar")
    public ResponseEntity<?> sumarStock(@RequestParam Long prendaId, @RequestParam Long tallaId, @RequestParam Integer cantidad) {
        int filas = prendaTallaDao.sumarStock(prendaId, tallaId, cantidad);
        if (filas == 0) {
            return msg.Personalizado("Combinación prenda-talla no encontrada.");
        }
        PrendaTalla prendaTalla = prendaTallaDao.findByPrenda_IdAndTalla_Id(prendaId, tallaId);
        return msg.Put(prendaTalla);
    }

    @GetMapping("/prenda-tallas")
    public ResponseEntity<?> showAll() {
        List<PrendaTalla> lista = prendaTallaService.getPrendaTallas();
        if (lista.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(lista);
    }

    @GetMapping("/prenda-talla/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        PrendaTalla prendaTalla = prendaTallaService.getPrendaTalla(id);
        if (prendaTalla == null) {
            return msg.NoGet();
        }
        return msg.Get(prendaTalla);
    }

    @PostMapping("/prenda-talla")
    public ResponseEntity<?> create(@RequestBody PrendaTallaDto dto) {
        try {
            PrendaTalla prendaTalla = prendaTallaService.save(dto);
            return msg.Post(prendaTalla);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/prenda-talla/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PrendaTallaDto dto) {
        try {
            if (prendaTallaService.existsById(id)) {
                dto.setId(id);
                PrendaTalla prendaTalla = prendaTallaService.update(dto);
                return msg.Put(prendaTalla);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException  e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/prenda-talla/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            PrendaTalla prendaTalla = prendaTallaService.getPrendaTalla(id);
            prendaTallaService.deletePrendaTalla(prendaTalla);
            return msg.Delete(prendaTalla);
        } catch (DataAccessException  e) {
            return msg.Error(e);
        }
    }
}
