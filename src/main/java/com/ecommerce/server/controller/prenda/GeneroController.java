package com.ecommerce.server.controller.prenda;

import com.ecommerce.server.model.dao.prenda.GeneroDao;
import com.ecommerce.server.model.dto.PageResponseDto;
import com.ecommerce.server.model.dto.prenda.GeneroDto;
import com.ecommerce.server.model.entity.prenda.Genero;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.prenda.IGeneroService;
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
public class GeneroController {
    @Autowired
    private IGeneroService generoService;

    private Mensajes msg = new Mensajes();

    @Autowired
    private GeneroDao generoDao;

    @GetMapping("/generos/paginado")
    public ResponseEntity<?> getGenerosPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Genero> generos = generoDao.findAll(pageable);
            if (generos.isEmpty()) {
                return msg.NoGet();
            }
            PageResponseDto<Genero> response = new PageResponseDto<>(
                    generos.getContent(),
                    generos.getNumber(),
                    generos.getSize(),
                    generos.getTotalElements(),
                    generos.getTotalPages()
            );
            return msg.Get(response);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @GetMapping("/generos")
    public ResponseEntity<?> showAll() {
        List<Genero> getList = generoService.getGeneros();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/genero/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Genero genero = generoService.getGenero(id);
        if (genero == null) {
            return msg.NoGet();
        }
        return msg.Get(GeneroDto.builder()
                .id(genero.getId())
                .nomGenero(genero.getNomGenero())
                .build());
    }

    @PostMapping("/genero")
    public ResponseEntity<?> create(@RequestBody GeneroDto generoDto) {
        Genero generoSave = null;
        try {
            generoSave = generoService.save(generoDto);
            return msg.Post(GeneroDto.builder()
                    .id(generoSave.getId())
                    .nomGenero(generoSave.getNomGenero())
                    .build());
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PutMapping("/genero/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GeneroDto generoDto) {
        Genero generoUpdate = null;
        try {
            if (generoService.existsById(id)) {
                generoDto.setId(id);
                generoUpdate = generoService.save(generoDto);
                return msg.Put(GeneroDto.builder()
                        .id(generoUpdate.getId())
                        .nomGenero(generoDto.getNomGenero())
                        .build());
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @DeleteMapping("/genero/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Genero generoDelete = generoService.getGenero(id);
            generoService.deleteGenero(generoDelete);
            return msg.Delete(generoDelete);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
}