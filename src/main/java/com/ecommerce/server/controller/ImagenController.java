package com.ecommerce.server.controller;

import com.ecommerce.server.model.entity.prenda.Imagen;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class ImagenController {

    @Autowired
    private IImagenService imagenService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/imagenes")
    public ResponseEntity<?> showAll() {
        List<Imagen> getList = imagenService.getImagenes();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Imagen imagen = imagenService.getImagen(id);
        if (imagen == null) {
            return msg.NoGet();
        }
        return msg.Get(imagen);
    }

    @PostMapping("/imagen")
    public ResponseEntity<?> create(@RequestBody Imagen imagen) {
        try {
            Imagen imagenSave = imagenService.save(imagen);
            return msg.Post(imagenSave);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/imagen/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Imagen imagen) {
        try {
            if (imagenService.existsById(id)) {
                imagen.setId(id);
                Imagen imagenUpdate = imagenService.update(imagen);
                return msg.Put(imagenUpdate);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/imagen/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Imagen imagenDelete = imagenService.getImagen(id);
            imagenService.deleteImagen(imagenDelete);
            return msg.Delete(imagenDelete);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
}