package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dao.prenda.TallaDao;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.dto.prenda.TallaDto;
import com.ecommerce.server.model.entity.prenda.Talla;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.ITallaService;
import org.bouncycastle.oer.its.etsi102941.TlmEntry;
import org.hibernate.annotations.UpdateTimestamp;
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
public class TallaController {

    @Autowired
    private ITallaService tallaService;

    private Mensajes msg = new Mensajes();
    @Autowired
    private TallaDao tallaDao;

    @GetMapping("/tallas/paginado")
    public ResponseEntity<?> getTallasPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Talla> tallas = tallaDao.findAll(pageable);
            if (tallas.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<Talla> response = new PageResponseDto<>(
                    tallas.getContent(),
                    tallas.getNumber(),
                    tallas.getSize(),
                    tallas.getTotalElements(),
                    tallas.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

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
