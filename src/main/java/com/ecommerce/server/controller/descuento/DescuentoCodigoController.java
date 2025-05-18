package com.ecommerce.server.controller.descuento;

import com.ecommerce.server.model.dto.descuento.AplicarDescuentoRequest;
import com.ecommerce.server.model.dto.descuento.DescuentoCodigoDto;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.descuento.IDescuentoCodigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class DescuentoCodigoController {

    @Autowired
    private IDescuentoCodigoService descuentoCodigoService;


    private Mensajes msg = new Mensajes();


    @GetMapping("/descuento-codigos")
    public ResponseEntity<?> showAll(){
        List<DescuentoCodigo> getList = descuentoCodigoService.getDescuentoCodigos();
        if(getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/descuento-codigo/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        DescuentoCodigo descuentoCodigo = descuentoCodigoService.getDescuentoCodigo(id);
        if(descuentoCodigo == null){
            return msg.NoGetId();
        }
        return msg.Get(DescuentoCodigoDto.builder()
                .id(descuentoCodigo.getId())
                .codigo(descuentoCodigo.getCodigo())
                .descripcion(descuentoCodigo.getDescripcion())
                .porcentaje(descuentoCodigo.getPorcentaje())
                .fechaInicio(descuentoCodigo.getFechaInicio())
                .fechaFin(descuentoCodigo.getFechaFin())
                .usoMaximo(descuentoCodigo.getUsoMaximo())
                .usado(descuentoCodigo.getUsado())
                .activo(descuentoCodigo.getActivo())
                .build()) ;
    }

    @PostMapping("/descuento-codigo")
    public ResponseEntity<?> create(@RequestBody DescuentoCodigoDto descuentoCodigoDto){
        DescuentoCodigo descuentoCodigoSave = null;
        try {
            descuentoCodigoSave = descuentoCodigoService.save(descuentoCodigoDto);
            return msg.Post(DescuentoCodigoDto.builder()
                    .id(descuentoCodigoSave.getId())
                    .codigo(descuentoCodigoSave.getCodigo())
                    .descripcion(descuentoCodigoSave.getDescripcion())
                    .porcentaje(descuentoCodigoSave.getPorcentaje())
                    .fechaInicio(descuentoCodigoSave.getFechaInicio())
                    .fechaFin(descuentoCodigoSave.getFechaFin())
                    .usoMaximo(descuentoCodigoSave.getUsoMaximo())
                    .usado(descuentoCodigoSave.getUsado())
                    .activo(descuentoCodigoSave.getActivo())
                    .build());
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/descuento-codigo/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DescuentoCodigoDto descuentoCodigoDto){
        DescuentoCodigo descuentoCodigoUpdate = null;
        try{
            if(descuentoCodigoService.existsById(id)){
                descuentoCodigoDto.setId(id);
                descuentoCodigoUpdate = descuentoCodigoService.save(descuentoCodigoDto);
                return msg.Post(DescuentoCodigoDto.builder()
                        .id(descuentoCodigoUpdate.getId())
                        .codigo(descuentoCodigoUpdate.getCodigo())
                        .descripcion(descuentoCodigoUpdate.getDescripcion())
                        .porcentaje(descuentoCodigoUpdate.getPorcentaje())
                        .fechaInicio(descuentoCodigoUpdate.getFechaInicio())
                        .fechaFin(descuentoCodigoUpdate.getFechaFin())
                        .usoMaximo(descuentoCodigoUpdate.getUsoMaximo())
                        .usado(descuentoCodigoUpdate.getUsado())
                        .activo(descuentoCodigoUpdate.getActivo())
                        .build());

            }else{
                return msg.NoPut();
            }
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/descuento-codigo/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            DescuentoCodigo descuentoCodigoDelete = descuentoCodigoService.getDescuentoCodigo(id);
            descuentoCodigoService.deleteDescuentoCodigo(descuentoCodigoDelete);
            return msg.Delete(descuentoCodigoDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
    @PostMapping("/aplicar")
    public ResponseEntity<?> aplicarDescuento(@RequestBody AplicarDescuentoRequest aplicarDescuentoRequest) {
        try {
            DescuentoCodigo descuento = descuentoCodigoService.aplicarCodigoDescuento(aplicarDescuentoRequest.getCodigo(), aplicarDescuentoRequest.getUsuarioId());
            if (descuento == null) {
                return msg.Personalizado("El usuario ya hizo uso del codigo");
            }
            return msg.Put(descuento);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

}
