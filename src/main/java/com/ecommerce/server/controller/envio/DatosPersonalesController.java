package com.ecommerce.server.controller.envio;

import com.ecommerce.server.model.dao.envio.DatosPersonalesDao;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.dto.envio.DatosPersonalesDto;
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.envio.IDatosPersonalesService;
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
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class DatosPersonalesController {

    @Autowired
    private IDatosPersonalesService datosPersonalesService;

    private Mensajes msg = new Mensajes();

    @Autowired
    private DatosPersonalesDao datosPersonalesDao;

    @GetMapping("/datos-personales/paginado")
    public ResponseEntity<?> getDatosPersonalesPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<DatosPersonales> datos = datosPersonalesDao.findAll(pageable);
            if (datos.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<DatosPersonales> response = new PageResponseDto<>(
                    datos.getContent(),
                    datos.getNumber(),
                    datos.getSize(),
                    datos.getTotalElements(),
                    datos.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
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
                    .email(datosPersonales.getEmail())
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
