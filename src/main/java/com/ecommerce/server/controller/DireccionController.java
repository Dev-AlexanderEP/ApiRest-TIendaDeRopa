package com.ecommerce.server.controller;

import com.ecommerce.server.model.dao.DireccionDao;
import com.ecommerce.server.model.dto.DireccionDto;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.entity.Direccion;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.IDireccionService;
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
public class DireccionController {

    @Autowired
    private IDireccionService direccionService;
    @Autowired
    private DireccionDao direccionDao;

    private Mensajes msg = new Mensajes();


    @GetMapping("/direcciones/paginado")
    public ResponseEntity<?> getDireccionesPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<Direccion> direcciones = direccionDao.findAll(pageable);
            if (direcciones.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<Direccion> response = new PageResponseDto<>(
                    direcciones.getContent(),
                    direcciones.getNumber(),
                    direcciones.getSize(),
                    direcciones.getTotalElements(),
                    direcciones.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/direcciones/usuario/{usuarioId}")
    public ResponseEntity<?> getDireccionesPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Direccion> direcciones = direccionDao.findByUsuarioId(usuarioId);
            if (direcciones.isEmpty()) {
                return msg.NoGet();
            }
            return msg.Get(direcciones);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
    @GetMapping("/direcciones")
    public ResponseEntity<?> showAll(){
        List<Direccion> getList = direccionService.getDirecciones();
        if (getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/direccion/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        Direccion direccion = direccionService.getDireccion(id);
        if (direccion == null){
            return msg.NoGetId();
        }
        return msg.Get(DireccionDto.builder()
                .id(direccion.getId())
                .nombres(direccion.getNombres())
                .apellidos(direccion.getApellidos())
                .usuarioId(direccion.getUsuarioId())
                .dni(direccion.getDni())
                .departamento(direccion.getDepartamento())
                .provincia(direccion.getProvincia())
                .distrito(direccion.getDistrito())
                .calle(direccion.getCalle())
                .detalle(direccion.getDetalle())
                .telefono(direccion.getTelefono())
                .build());
    }

    @PostMapping("/direccion")
    public ResponseEntity<?> create(@RequestBody DireccionDto direccionDto){
        Direccion direccionSave = null ;
        try{
            direccionSave = direccionService.save(direccionDto);
            return msg.Post(DireccionDto.builder()
                    .id(direccionSave.getId())
                    .nombres(direccionSave.getNombres())
                    .apellidos(direccionSave.getApellidos())
                    .usuarioId(direccionSave.getUsuarioId())
                    .dni(direccionSave.getDni())
                    .departamento(direccionSave.getDepartamento())
                    .provincia(direccionSave.getProvincia())
                    .distrito(direccionSave.getDistrito())
                    .calle(direccionSave.getCalle())
                    .detalle(direccionSave.getDetalle())
                    .telefono(direccionSave.getTelefono())
                    .build());
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

    @PutMapping("/direccion/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DireccionDto direccionDto){
        Direccion direccionUpdate = null;
        try{
            if (direccionService.existsById(id)){
                direccionDto.setId(id);
                direccionUpdate = direccionService.save(direccionDto);
                return msg.Put(DireccionDto.builder()
                        .id(direccionUpdate.getId())
                        .nombres(direccionUpdate.getNombres())
                        .apellidos(direccionUpdate.getApellidos())
                        .usuarioId(direccionUpdate.getUsuarioId())
                        .dni(direccionUpdate.getDni())
                        .departamento(direccionUpdate.getDepartamento())
                        .provincia(direccionUpdate.getProvincia())
                        .distrito(direccionUpdate.getDistrito())
                        .calle(direccionUpdate.getCalle())
                        .detalle(direccionUpdate.getDetalle())
                        .telefono(direccionUpdate.getTelefono())
                        .build());
            }else{
                return msg.NoPut();
            }
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

    @DeleteMapping("/direccion/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            Direccion direccion = direccionService.getDireccion(id);
            direccionService.delete(direccion);
            return msg.Delete(direccion);
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }
}
