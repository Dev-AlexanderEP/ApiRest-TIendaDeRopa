package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dao.prenda.MarcaDao;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.dto.prenda.MarcaDto;
import com.ecommerce.server.model.entity.prenda.Marca;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.IMarcaService;
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
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class MarcaController {
    @Autowired
    private IMarcaService marcaService;

    private Mensajes msg = new Mensajes();

    @Autowired
    private MarcaDao marcaDao;

    @GetMapping("/marcas/paginado")
    public ResponseEntity<?> getMarcasPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Marca> marcas = marcaDao.findAll(pageable);
            if (marcas.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<Marca> response = new PageResponseDto<>(
                    marcas.getContent(),
                    marcas.getNumber(),
                    marcas.getSize(),
                    marcas.getTotalElements(),
                    marcas.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/marcas")
    public ResponseEntity<?> showAll() {
        List<Marca> getList = marcaService.getMarcas();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return  msg.Get(getList);
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Marca marca = marcaService.getMarca(id);
        if (marca == null) {
            return msg.NoGet();
        }
        return  msg.Get(MarcaDto.builder()
                .id(marca.getId())
                .nomMarca(marca.getNomMarca())
                .build());
    }

    @PostMapping("/marca")
    public ResponseEntity<?> create(@RequestBody MarcaDto marcaDto) {
        Marca marcaSave = null;
        try{
            marcaSave = marcaService.save(marcaDto);
            return msg.Post(MarcaDto.builder()
                    .id(marcaSave.getId())
                    .nomMarca(marcaSave.getNomMarca())
                    .build());
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/marca/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MarcaDto marcaDto) {
        Marca marcaUpdate = null;
        try{
            if (marcaService.existsById(id)) {
                marcaDto.setId(id);
                marcaUpdate = marcaService.save(marcaDto);
                return msg.Put(MarcaDto.builder()
                        .id(marcaUpdate.getId())
                        .nomMarca(marcaDto.getNomMarca())
                        .build());
            }else{
                return  msg.NoPut();
            }
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }
//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/marca/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            Marca marcaDelete = marcaService.getMarca(id);
            marcaService.deleteMarca(marcaDelete);
            return msg.Delete(marcaDelete);
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

}
