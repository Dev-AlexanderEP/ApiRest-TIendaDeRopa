package com.ecommerce.server.controller.envio;

import com.ecommerce.server.model.dto.envio.DatosPersonalesDto;
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.envio.IDatosPersonalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class DatosPersonalesController {

    @Autowired
    private IDatosPersonalesService datosPersonalesService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/datos-personales")
    public ResponseEntity<?> showAll() {
        try {
            List<DatosPersonales> datosPersonalesList = datosPersonalesService.getDatosPersonales();
            if (datosPersonalesList.isEmpty()) {
                return msg.NoGet();
            }
            return msg.Get(datosPersonalesList);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/dato-personal/{id}")
    public ResponseEntity<?> showPersonal(@PathVariable Long id) {
        try {
            DatosPersonales datosPersonales = datosPersonalesService.getDatoPersonal(id);
            if (datosPersonales == null) {
                return msg.NoGet();
            }
            DatosPersonalesDto datosPersonalesDto = DatosPersonalesDto.builder()
                    .id(datosPersonales.getId())
                    .nombres(datosPersonales.getNombres())
                    .apellidos(datosPersonales.getApellidos())
                    .usuarioId(datosPersonales.getUsuarioId())
                    .dni(datosPersonales.getDni())
                    .departamento(datosPersonales.getDepartamento())
                    .provincia(datosPersonales.getProvincia())
                    .distrito(datosPersonales.getDistrito())
                    .calle(datosPersonales.getCalle())
                    .detalle(datosPersonales.getDetalle())
                    .telefono(datosPersonales.getTelefono())
                    .build();
            return msg.Get(datosPersonalesDto);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PostMapping("/dato-personal")
    public ResponseEntity<?> create(@RequestBody DatosPersonalesDto datosPersonalesDto) {
        try {
            DatosPersonales save = datosPersonalesService.save(datosPersonalesDto);
            return msg.Post(save);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/dato-personal/{id}")
    public ResponseEntity<?> update(@RequestBody DatosPersonalesDto datosPersonalesDto, @PathVariable Long id) {
        try {
            if (datosPersonalesService.existsById(id)) {
                datosPersonalesDto.setId(id); // Actualizar el ID en el DTO
                DatosPersonales updated = datosPersonalesService.save(datosPersonalesDto);
                return msg.Put(updated);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/dato-personal/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            DatosPersonales datosPersonales = datosPersonalesService.getDatoPersonal(id);
            datosPersonalesService.delete(datosPersonales);
            return msg.Delete(datosPersonales);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
}
