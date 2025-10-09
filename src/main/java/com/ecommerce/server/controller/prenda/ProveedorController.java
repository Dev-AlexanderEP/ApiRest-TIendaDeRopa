package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dao.prenda.ProveedorDao;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.dto.prenda.ProveedorDto;
import com.ecommerce.server.model.entity.prenda.Proveedor;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.IProveedorService;
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
        "http://localhost:4200",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe",
        "*"
})
@RestController
@RequestMapping("/api/v1")
public class ProveedorController {
    @Autowired
    private IProveedorService proveedorService;

    private Mensajes msg = new Mensajes();

    @Autowired
    private ProveedorDao proveedorDao;
    @GetMapping("/proveedores/paginado")
    public ResponseEntity<?> getProveedoresPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Proveedor> proveedores = proveedorDao.findAll(pageable);
            if (proveedores.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<Proveedor> response = new PageResponseDto<>(
                    proveedores.getContent(),
                    proveedores.getNumber(),
                    proveedores.getSize(),
                    proveedores.getTotalElements(),
                    proveedores.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
    @GetMapping("/proveedores")
    public ResponseEntity<?> showAll() {
        List<Proveedor> getList = proveedorService.getProveedores();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/proveedor/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.getProveedor(id);
        if (proveedor == null) {
            return msg.NoGet();
        }
        return msg.Get(ProveedorDto.builder()
                .id(proveedor.getId())
                .nomProveedor(proveedor.getNomProveedor())
                .build());
    }

    @PostMapping("/proveedor")
    public ResponseEntity<?> create(@RequestBody ProveedorDto proveedorDto) {
        Proveedor proveedorSave = null;
        try{
            proveedorSave = proveedorService.save(proveedorDto);
             return msg.Post(ProveedorDto.builder()
                    .id(proveedorSave.getId())
                    .nomProveedor(proveedorSave.getNomProveedor())
                    .build());
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/proveedor/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProveedorDto proveedorDto) {
        Proveedor proveedorUpdate = null;
        try {
            if (proveedorService.existsById(id)){
                proveedorDto.setId(id);
                proveedorUpdate = proveedorService.save(proveedorDto);
                return msg.Put(ProveedorDto.builder()
                        .id(proveedorUpdate.getId())
                        .nomProveedor(proveedorUpdate.getNomProveedor())
                        .build());
            }else {
                return  msg.NoPut();
            }
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/proveedor/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Proveedor proveedorDelete = proveedorService.getProveedor(id);
            proveedorService.deleteProveedor(proveedorDelete);
            return msg.Delete(proveedorDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
}
