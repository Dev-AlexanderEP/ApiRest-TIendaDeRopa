package com.ecommerce.server.controller.envio;

import com.ecommerce.server.model.dto.envio.EnvioDto;
import com.ecommerce.server.model.entity.envio.Envio;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.envio.IEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class EnvioController {

    @Autowired
    private IEnvioService envioService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/envios")
    public ResponseEntity<?> showAll() {
        try {
            List<Envio> envios = envioService.getEnvios();
            if (envios.isEmpty()) {
                return msg.NoGet();
            }

            return msg.Get(envios);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/envio/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Envio envio = envioService.getEnvio(id);
            if (envio == null) {
                return msg.NoGet();
            }
            return msg.Get(envio);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PostMapping("/envio")
    public ResponseEntity<?> create(@RequestBody EnvioDto envioDto) {
        try {
            Envio saved = envioService.save(envioDto);
            return msg.Post(saved);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/envio/{id}")
    public ResponseEntity<?> envioUpdate(@PathVariable Long id, @RequestBody EnvioDto envioDto) {
        try {
            if (envioService.existsById(id)) {
                envioDto.setId(id); // Actualizar el ID en el DTO
                Envio updated = envioService.save(envioDto);
                return msg.Put(updated);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/envio/{id}")
    public ResponseEntity<?> envioDelete(@PathVariable Long id) {
        try {
            Envio envio = envioService.getEnvio(id);
            envioService.delete(envio);
            return msg.Delete(envio);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

}
